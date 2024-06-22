package com.task.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.model.Submission;
import com.task.model.TaskDto;
import com.task.model.UserDto;
import com.task.repository.SubmissionRepository;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	@Autowired
	private SubmissionRepository submissionRepository;
	
	@Autowired 
	private TaskService taskService;
	
	@Autowired UserService userService;
	
	@Override
	public Submission submitTask(Long taskId, String githubLink, String jwt) throws Exception {
		UserDto user = userService.getUserProfile(jwt);
		TaskDto task = taskService.getTaskById(taskId,jwt);
		if(task!= null) {
			Submission submission = new Submission();
			submission.setTaskId(taskId);
			submission.setUserId(user.getId());
			submission.setGithubLink(githubLink);
			submission.setSubmissionTime(LocalDateTime.now());
			return submissionRepository.save(submission);
		}
		throw new Exception("Task not found with id: "+taskId);
	}

	@Override
	public Submission getTaskSubmissionById(Long submissionId) throws Exception {
		return submissionRepository.findById(submissionId).orElseThrow(()-> new Exception("Task Submission with id : "+submissionId+" not found"));
	}

	@Override
	public List<Submission> getAllTaskSubmissions() {
		return submissionRepository.findAll();
	}

	@Override
	public List<Submission> getTaskSubmissionByTaskId(Long taskId) {
		return submissionRepository.findByTaskId(taskId);
	}
	
	@Override
	public Submission acceptDeclineSubmission(long id, String status) throws Exception {
		Submission submission = getTaskSubmissionById(id);
		submission.setStatus(status);
		if(status.equals("ACCEPT"))
			taskService.completeTask(submission.getTaskId());
		return submissionRepository.save(submission);
	}

}
