package com.task.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.model.Task;
import com.task.model.TaskStatus;
import com.task.repository.TaskRepository;
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public Task createTask(Task task, String requesterRole) throws Exception {
		if(!requesterRole.equals("ROLE_ADMIN")){
			throw new Exception("Only Admin can create Task");
		}
		task.setStatus(TaskStatus.PENDING);
		task.setCreatedAt(LocalDateTime.now());
		return taskRepository.save(task);
	}

	@Override
	public Task getTaskById(Long id) throws Exception {
		return taskRepository.findById(id).orElseThrow(()->new Exception("Task Not Found with id : "+id));
	}

	@Override
	public List<Task> getAllTask(TaskStatus status) {
		List<Task> allTask = taskRepository.findAll();
		List<Task> filteredTask = allTask.stream().filter(
				task->status==null|| task.getStatus().name().equalsIgnoreCase(status.toString())
		).collect(Collectors.toList());
		return filteredTask;
	}

	@Override
	public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
		Task existingTask = getTaskById(id);
		existingTask.setTitle(updatedTask.getTitle() != null ? updatedTask.getTitle() : existingTask.getTitle());
		existingTask.setImage(updatedTask.getImage()!=null? updatedTask.getImage():existingTask.getImage());
		existingTask.setDescription(updatedTask.getDescription()!=null? updatedTask.getDescription():existingTask.getDescription());
		existingTask.setStatus(updatedTask.getStatus()!=null? updatedTask.getStatus():existingTask.getStatus());
		existingTask.setDeadline(updatedTask.getDeadline()!=null? updatedTask.getDeadline():existingTask.getDeadline());
		
		
		return taskRepository.save(existingTask);
	}

	@Override
	public void deleteTask(Long id) throws Exception {
		getTaskById(id);
		taskRepository.deleteById(id);
	}

	@Override
	public Task assignedToUser(Long userId, Long taskId) throws Exception {
		Task task = getTaskById(taskId);
		task.setAssignedUserId(userId);
		task.setStatus(TaskStatus.ASSIGNED);
		return taskRepository.save(task);
	}

	@Override
	public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
		List<Task> allTask = taskRepository.findByAssignedUserId(userId);
		List<Task> filteredTask = allTask.stream().filter(
				task->status==null|| task.getStatus().name().equalsIgnoreCase(status.toString())
		).collect(Collectors.toList());
		return filteredTask;
	}

	@Override
	public Task completeTask(Long taskId) throws Exception {
		Task task = getTaskById(taskId);
		task.setStatus(TaskStatus.DONE);
		return taskRepository.save(task);
	}

}
