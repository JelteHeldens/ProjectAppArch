package com.AppArch.Project.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.AppArch.Project.Model.State;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;
import com.AppArch.Project.Model.UserAuthorization;
import com.AppArch.Project.Service.OfferRepoService;
import com.AppArch.Project.Service.TaskRepoService;
import com.AppArch.Project.Service.UserRepoService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	
	@Autowired
	private UserRepoService UserRepS;
	
	@Autowired
	private TaskRepoService taskRepS;
	
	@Autowired
	private OfferRepoService offerRepoS;
	
	@Autowired
	private ServletContext ctx;
	
	
	//Drie get mappings naar homepage: index.html	
	@GetMapping("/")
	public String index(HttpServletRequest request)
	{
		List<Task> tasks = taskRepS.findOpenTasks();
        Map<Task, Object> taskData = new HashMap<>();
        for(int i=0; i<tasks.size(); i++) {
        	int offers = offerRepoS.getNumberTaskByTaskId(tasks.get(i));
        	taskData.put(tasks.get(i), offers);
        }
		ctx.setAttribute("Indextasks",taskData);
        
		ctx.setAttribute("offers", offerRepoS.getAll());
		if(request.isUserInRole("ROLE_klusjesman")){
			return "klusjesman/index";
		}
		else {
			return "klant/index";
		}
	}
	@GetMapping("/index")
	public String home(HttpServletRequest request)
	{
		return "redirect:/";
	}
	@GetMapping("/home")
	public String homep(HttpServletRequest request)
	{
		return "redirect:/";
	}
	
	@RequestMapping("/login")
	public String inlog()
	{
		return "login";
	}
	
	@GetMapping("/register")
	public String reg()
	{
		return "register";
	}
	
	@GetMapping("/profile")
	public String profiles(HttpServletRequest request, Model m) {
		Optional<User> user = UserRepS.getUserById(UserRepS.getCurrentUser());
		ctx.setAttribute("user",user.get());
		
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		//Gebruiker is klusjesman
		if (request.isUserInRole("ROLE_klusjesman")) {
			ctx.setAttribute("role","klusjesman");
			//Rating ophalen geeft error als klusjesman nog geen klusjes heeft volbracht -> Rating wordt dan = NA/5
			try {
				float rating = taskRepS.getAverageRating(user.get());
				ctx.setAttribute("rating", String.format("%.1f", rating));
			}
			catch(Exception e) {
				String rating = "NA";
				ctx.setAttribute("rating", rating);
			}
			
			List<Task> userTasksGEBODEN = new ArrayList<>();
			List<Task> userTasksTOEGEWEZEN = new ArrayList<>();
			List<Task> userTasksUITGEVOERD = new ArrayList<>();
			List<Task> userTasksBEOORDEELD = new ArrayList<>();
			List<Task> userTasks = offerRepoS.findTasksByEmail(user.get());
			for (int i = 0; i < userTasks.size(); i++){
				Task t = userTasks.get(i);
				if      (t.getStatus() == State.GEBODEN) {userTasksGEBODEN.add(t);}
				else if (t.getStatus() == State.TOEGEWEZEN && t.getExecutor() == user.get()) {userTasksTOEGEWEZEN.add(t);}
				else if (t.getStatus() == State.UITGEVOERD && t.getExecutor() == user.get()) {userTasksUITGEVOERD.add(t);}
				else if (t.getStatus() == State.BEOORDEELD && t.getExecutor() == user.get()) {userTasksBEOORDEELD.add(t);}
			}
	        Map<Task, Object> tasksGEBODEN = new HashMap<>();
	        for(int i=0; i<userTasksGEBODEN.size(); i++) {
	        	int offers = offerRepoS.getNumberTaskByTaskId(userTasksGEBODEN.get(i));
	        	tasksGEBODEN.put(userTasksGEBODEN.get(i), offers);
	        }
			ctx.setAttribute("userTasksGEBODEN",   tasksGEBODEN);
			ctx.setAttribute("userTasksTOEGEWEZEN",userTasksTOEGEWEZEN);
			ctx.setAttribute("userTasksUITGEVOERD",userTasksUITGEVOERD);
			ctx.setAttribute("userTasksDone",userTasksBEOORDEELD);
			
			return "klusjesman/profile";
		}
		//Gebruiker is klant
		else {
			ctx.setAttribute("role","klant");
			List<Task> userTasksTOEGEWEZEN = taskRepS.getUserTasksState(user.get(), State.TOEGEWEZEN);
			ctx.setAttribute("userTasksTOEGEWEZEN",userTasksTOEGEWEZEN);
			List<Task> userTasksUITGEVOERD = taskRepS.getUserTasksState(user.get(), State.UITGEVOERD);
			ctx.setAttribute("userTasksUITGEVOERD",userTasksUITGEVOERD);
			List<Task> userTasksDone = taskRepS.getUserTasksDone(user.get());
			ctx.setAttribute("userTasksDone",userTasksDone);
			List<Task> userTasksOpenstaand = taskRepS.getUserTasksState(user.get(), State.BESCHIKBAAR);
			ctx.setAttribute("userTasksOpenstaand",userTasksOpenstaand);
			List<Task> userTasksGeboden = taskRepS.getUserTasksState(user.get(), State.GEBODEN);
			Map<Task, Object> tasksGEBODEN = new HashMap<>();
	        for(int i=0; i<userTasksGeboden.size(); i++) {
	        	int offers = offerRepoS.getNumberTaskByTaskId(userTasksGeboden.get(i));
	        	tasksGEBODEN.put(userTasksGeboden.get(i), offers);
	        }
			ctx.setAttribute("userTasksGEBODEN",   tasksGEBODEN);
			
			return "klant/profile";
		}
	}
	
	@PostMapping("/registreer")
	public String registreer(@Valid User u, BindingResult result, HttpServletRequest req, Model m) { //BindingResult is het resultaat van de valid als dus error is dan kan je dat uit bingingresult halen
	    if (result.hasErrors()) {
	    	m.addAttribute("error", "Fill in all fields correctly.");
	        return "register";
	    }
	    else if(UserRepS.existsByEmail(u.getEmail())) {
	    	m.addAttribute("error", "Email already in use.");
	    	return "register";
	    }
		RestTemplate rest = new RestTemplate();
		//User u = new User(req.getParameter("name"),req.getParameter("email"),req.getParameter("pswd"),req.getParameter("userType"),1);
		rest.postForObject("http://localhost:8080/user/add",u , ResponseEntity.class);
		UserAuthorization ua = new UserAuthorization(u.getEmail(),req.getParameter("functie"));
		rest.postForObject("http://localhost:8080//user/add/authorities", ua,ResponseEntity.class) ;
		return "redirect:/login";
	}
	

	
	@PostMapping("/edit/profiel")
	public String editProfile(HttpServletRequest req) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRepS.updateUser(email, req.getParameter("NEWNAME"));
		return "redirect:/profile";
	}
	

	
	@GetMapping("/info")
	public String info(HttpSession ses)
	{
		return "info";
	}
	
}
