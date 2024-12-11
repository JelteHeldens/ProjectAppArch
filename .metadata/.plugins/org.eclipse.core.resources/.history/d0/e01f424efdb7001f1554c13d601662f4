package com.AppArch.Project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;
import com.AppArch.Project.Service.TaskRepoService;
import com.AppArch.Project.Service.UserRepoService;



@RestController
public class ApiController {
	@Autowired
	private UserRepoService userServ;
	
	@Autowired
	private TaskRepoService TaskServ;
	
	@PostMapping("/user/add")
	public void addUser(@RequestBody User u){
		userServ.addUser(u);
	}
	
	@GetMapping("/users")
	public List<User> getusers(){
		return userServ.getusers();
	}
	
	@GetMapping("/tasks")
	public List<Task> getTasks(){
		return TaskServ.getTask();
	}
	
	@PostMapping("/tasks/add")
	public void addTasks(@RequestBody Task t){
		System.out.println("Received Task: " + t);
		TaskServ.addTask(t);
	}
	
	
	
}
