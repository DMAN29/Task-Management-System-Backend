package com.task.service;

import java.util.List;

import com.task.model.Submission;

public interface SubmissionService {

	Submission submitTask(Long taskId, String githubLink, String jwt)throws Exception;
	
	Submission getTaskSubmissionById(Long submissionId) throws Exception;
	
	List<Submission> getAllTaskSubmissions();
	
	List<Submission> getTaskSubmissionByTaskId(Long taskId);
	
	Submission acceptDeclineSubmission(long id,String status) throws Exception;
}
