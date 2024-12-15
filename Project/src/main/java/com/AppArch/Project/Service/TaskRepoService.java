package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.AppArch.Project.Model.State;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;

public interface TaskRepoService {
	public List<Task> getTasks();
	public List<Task> getUserTasks(User u);
	public List<Task> getUserTasksState(User u, State s);
	public List<Task> getUserTasksStateLessThan(User u, State s);
	public List<Task> getUserTasksActive(User u);
	public List<Task> getUserTasksDone(User u);
	public List<Task> findOpenTasks();
	public List<Task> findClosedTasks();
	public void addTask(Task t);
	public Optional<Task> getTaskById(int id);
	public void updateTask(int id, String title, String description, float price);
	public void remove(int id);
	public void changeState(int id, State s);
	public void addExecutor(int id, User executor);
	
}
