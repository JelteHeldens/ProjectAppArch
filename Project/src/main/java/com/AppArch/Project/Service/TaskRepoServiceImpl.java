package com.AppArch.Project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Repository.TaskRepo;
@Service
public class TaskRepoServiceImpl implements TaskRepoService{
	@Autowired
	private TaskRepo taskRepo;
	
	public List<Task> getTask(){
		return taskRepo.findAll();
	}

	public void addTask(Task t) {
		System.out.println("save Task: " + t);
		taskRepo.save(t);
	}
	
}