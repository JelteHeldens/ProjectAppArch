package com.AppArch.Project.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;
import com.AppArch.Project.Model.OfferKey;
import com.AppArch.Project.Model.State;
import com.AppArch.Project.Service.OfferRepoService;
import com.AppArch.Project.Service.TaskRepoService;
import com.AppArch.Project.Service.UserRepoService;

import org.springframework.ui.Model;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TaskController {
	
	@Autowired
	private UserRepoService UserRepS;
	
	@Autowired
	private TaskRepoService taskRepS;
	
	@Autowired
	private OfferRepoService offerRepoS;
	
	@Autowired
	private ServletContext ctx;
	
	
	@GetMapping("/newJob")
	public String newJob(HttpSession ses)
	{
		return "newJob";
	}
	
	@PostMapping("/taskform")
	public String addtasks(HttpServletRequest req) {
		RestTemplate rest = new RestTemplate();
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //Email ophalen van user
		Optional<User> u = UserRepS.getUserById(email);
		if (!u.isPresent()) {
		    throw new RuntimeException("User not found");
		}
		Task task = new Task(req.getParameter("title"),
                req.getParameter("description"),
                Float.parseFloat(req.getParameter("price")),
                u.get());
		
		rest.postForObject("http://localhost:8080/tasks/add", task, ResponseEntity.class);

		ctx.setAttribute("tasks",taskRepS.getTasks());
		return "redirect:/";
	}
	

    @GetMapping("/taskDetail/{id}")
    public String getTaskDetail(@PathVariable int id, Model m) {
        Optional<Task> task = taskRepS.getTaskById(id);
        if(task.get().getStatus() == State.BESCHIKBAAR){
	        m.addAttribute("task", task.get());
	        return "klant/task";
        }
        else {
        	return "redirect:/profile";
        }
    }
    
    @PostMapping("/taskedite")
    public String editTask(HttpServletRequest req) {
    	RestTemplate rest = new RestTemplate();
        
    	int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        float price = Float.parseFloat(req.getParameter("price"));

        //Map object om data van form in op te slaan
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("id", id);
        taskData.put("title", title);
        taskData.put("description", description);
        taskData.put("price", price);

        //Stuur POST request naar API
        rest.postForEntity("http://localhost:8080/tasks/update", taskData, Void.class);
        
    	return "redirect:/profile";
    }
    
    @GetMapping("/taskGebode/{id}")
	public String gebodeTask(@PathVariable int id, Model m) {
    	Optional<Task> t = taskRepS.getTaskById(id);
		List<User> GebodenUsers = offerRepoS.findUserByTask(t.get());
		//De mapping bestaat uit <User,Object> zodat "NA" als string ook kan gemapt worden als klusjesman nog geen rating heeft.
    	Map<User, Object> userRatings = new HashMap<>();
		for(int i=0; i<GebodenUsers.size(); i++) {
			//Rating ophalen geeft error als klusjesman nog geen klusjes heeft volbracht -> Rating wordt dan = NA/5
			try {
				float rating = taskRepS.getAverageRating(GebodenUsers.get(i));
				ctx.setAttribute("rating", String.format("%.1f", rating));
				userRatings.put(GebodenUsers.get(i), rating);
			}
			catch(Exception e) {
				String rating = "NA";
				ctx.setAttribute("rating", rating);
				userRatings.put(GebodenUsers.get(i), rating);
			}
		}
		m.addAttribute("userRatings",userRatings);
		m.addAttribute("huidigID", id);
		return "/klant/geboden";
	}
    
    @GetMapping("/taskReview/{id}")
	public String reviewTask(@PathVariable int id, Model m) {
    	Optional<Task> t = taskRepS.getTaskById(id);
    	 m.addAttribute("task", t.get());
		return "/klant/review";
	}
    
    //Rating toekennen aan task
    @PostMapping("/finalizeTask")
    public String finalizeTask(HttpServletRequest req) {
    	int id = Integer.parseInt(req.getParameter("id"));
    	float rating = Float.parseFloat(req.getParameter("rating"));
    	taskRepS.reviewTask(id, rating);
		taskRepS.changeState(id, State.BEOORDEELD);

    	return "redirect:/profile";
	}
    
    @PostMapping("/gebodeTask")
    public String userSelect(HttpServletRequest req) {
    	RestTemplate rest = new RestTemplate();
    	String klusjesmanEmail = req.getParameter("klusjesman");
    	int id = Integer.parseInt(req.getParameter("id"));
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("email", klusjesmanEmail);
    	rest.postForEntity("http://localhost:8080/toewijzing", params, Void.class);
    	return "redirect:/profile";
    }
    
	@PostMapping("/taskDelete")
	public String removeTask(HttpServletRequest req) {
		RestTemplate rest = new RestTemplate();
		rest.delete("http://localhost:8080/tasks/delete/"+req.getParameter("id"));
		return "redirect:/profile";
	}
	
	@PostMapping("/takeTask")
	public String bod(HttpServletRequest req) {
		RestTemplate rest = new RestTemplate();
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //Email ophalen van user
		int id = Integer.parseInt(req.getParameter("id"));
		
		Map<String, Object> offerData = new HashMap<>();
		offerData.put("id", id);
		offerData.put("email", email);
		
		rest.postForEntity("http://localhost:8080/offer/add", offerData, Void.class);
		return "redirect:/";
	}

	//Markeren van task als uitgevoerd door klusjesman
	@PostMapping("/completeTask")
	public String complete(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		
		taskRepS.changeState(id, State.UITGEVOERD);
		return "redirect:/profile";
	}
	
	@PostMapping("/bodIntrekken")
	public String bodIntrekken(HttpServletRequest req) {
		User u = UserRepS.getUserById(UserRepS.getCurrentUser()).get();
		int id = Integer.parseInt(req.getParameter("id"));
		Task t = taskRepS.getTaskById(id).get();
		if(t.getStatus() != State.GEBODEN) {
			return "redirect:/profile";
		}
		else {
			OfferKey k = new OfferKey(u,t);
			offerRepoS.deleteById(k);
			int nubmer = offerRepoS.getNumberTaskByTaskId(t);
			if (nubmer == 0) {
				taskRepS.changeState(id, State.BESCHIKBAAR);
			}
			return "redirect:/profile";
		}
	}

	

}
