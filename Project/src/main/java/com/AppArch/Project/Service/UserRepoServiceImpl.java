package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.User;
import com.AppArch.Project.Model.UserAuthorization;
import com.AppArch.Project.Repository.UserAthorizationRepo;
import com.AppArch.Project.Repository.UserRepo;

@Service
public class UserRepoServiceImpl implements UserRepoService{
	
	@Autowired
	private UserRepo UserRep;
	
	@Autowired
	private UserAthorizationRepo userAthRep;
	
	public void addUser(User u) {
		UserRep.save(u);
	}
	
	public List<User> getusers(){
		return UserRep.findAll();
	}

	public Optional<User> getUserById(String e) {
		return UserRep.findById(e);
		
	}
	
	public String getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	public void updateUser(String mail, String NEWname) {
		User u = UserRep.findById(mail).get();
		u.setName(NEWname);
		
		UserRep.save(u);
	}

	public void addAuth(UserAuthorization ua) {
		userAthRep.save(ua);
		
	}
}
