package com.AppArch.Project.Service;

import java.util.List;

import com.AppArch.Project.Model.Task;

public interface TaskRepoService {
	public List<Task> getTask();
	public void addTask(Task t);
}