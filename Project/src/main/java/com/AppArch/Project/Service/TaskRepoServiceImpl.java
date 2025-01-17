package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.State;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;
import com.AppArch.Project.Repository.TaskRepo;
@Service
public class TaskRepoServiceImpl implements TaskRepoService{
	@Autowired
	private TaskRepo taskRepo;
	
	public List<Task> getTasks(){
		return taskRepo.findAll();
	}
	public List<Task> getUserTasks(User u){
		return taskRepo.findByUser(u);
	}

	public List<Task> getUserTasksState(User u, State s){
		return taskRepo.findByUserAndState(u,s);
	}

	public List<Task> getUserTasksStateLessThan(User u, State s){
		return taskRepo.findByUserAndStateLessThan(u,s);
	}
	
	public List<Task> getUserTasksActive(User u){
		return taskRepo.findByUserAndActive(u);
	}
	
	public List<Task> getUserTasksDone(User u){
		return taskRepo.findByUserAndDone(u);
	}
	
	public void addTask(Task t) {
		System.out.println("save Task: " + t);
		taskRepo.save(t);
	}

	public Optional<Task> getTaskById(int id) {
			return taskRepo.findById(id);
	}
	
	public void updateTask(int id, String title, String description, float price) {
		taskRepo.updateTask(id, title, description, price);
	}
	
	public void remove(int id) {
		taskRepo.deleteById(id);
	}
	
	public void changeState(int id, State s) {
		taskRepo.changeState(id,s);
		
	}

	public void addExecutor(int id, User executor) {
		taskRepo.addExecutor(id, executor);
		
	}

	public List<Task> findOpenTasks() {
		return taskRepo.findOpenTasks();
	}

	public List<Task> findClosedTasks() {
		return taskRepo.findClosedTasks();
	}
	public void reviewTask(int id, float rating) {
		taskRepo.reviewTask(id, rating);
	}

	public float getAverageRating(User executor) {
		return taskRepo.getAverageRating(executor);
	}
	
	public void updateStatus(int id, State status) {
		taskRepo.updateStatus(id, status);
	}

	
}
