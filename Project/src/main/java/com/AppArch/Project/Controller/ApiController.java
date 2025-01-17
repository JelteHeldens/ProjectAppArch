package com.AppArch.Project.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AppArch.Project.Model.Offer;
import com.AppArch.Project.Model.State;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;
import com.AppArch.Project.Model.UserAuthorization;
import com.AppArch.Project.Service.OfferRepoService;
import com.AppArch.Project.Service.TaskRepoService;
import com.AppArch.Project.Service.UserRepoService;



@RestController
public class ApiController {
	@Autowired
	private UserRepoService userServ;
	
	@Autowired
	private TaskRepoService TaskServ;
	
	@Autowired
	private PasswordEncoder pswdEncoder;
	
	@Autowired
	private OfferRepoService offerRepoS;
	
	@PostMapping("/user/add")
	public void addUser(@RequestBody User u){
		u.setPasswd(pswdEncoder.encode(u.getPasswd()));
		userServ.addUser(u);
	}
	
	@PostMapping("/user/add/authorities")
	public void addAuthorities(@RequestBody UserAuthorization ua) {
		System.out.println(ua);
		userServ.addAuth(ua);
	}
	
	@GetMapping("/users")
	public List<User> getusers(){
		return userServ.getusers();
	}
	
	@GetMapping("/tasks")
	public List<Task> getTasks(){
		return TaskServ.getTasks();
	}
	
	@PostMapping("/tasks/add")
	public void addTasks(@RequestBody Task t){
		System.out.println("Received Task: " + t);
		TaskServ.addTask(t);
	}
	
	@PostMapping("/tasks/update")
    public void updateTaskFromForm(@RequestBody Map<String, Object> taskData) {
	    int id = (int) taskData.get("id");
	    String title = (String) taskData.get("title");
	    String description = (String) taskData.get("description");
	    float price = ((Number) taskData.get("price")).floatValue();
	    System.out.println(id);
	    
        TaskServ.updateTask(id, title, description, price);
	}
	
	@DeleteMapping("/tasks/delete/{id}")
	public void delTask(@PathVariable int id) {
		System.out.println(id);
		TaskServ.remove(id);
	}
	
	@PostMapping("/offer/add")
	 public void addoffers(@RequestBody Map<String, Object> offerData) {
		String email = (String) offerData.get("email");
		int id = (int) offerData.get("id");
		Optional<User> u = userServ.getUserById(email);
		Optional<Task> t = TaskServ.getTaskById(id);
		Offer o = new Offer(u.get(),t.get());
		offerRepoS.addOffer(o);
		TaskServ.changeState(id, State.GEBODEN);
	}
	
	@PostMapping("/toewijzing")
	public void toewijzing(@RequestBody Map<String, Object> data) {
		String email = (String) data.get("email");
		int id = (int) data.get("id");
    	User klusjesman = userServ.getUserById(email).get();
    	
    	TaskServ.addExecutor(id, klusjesman);
	}
	
	
	
}
