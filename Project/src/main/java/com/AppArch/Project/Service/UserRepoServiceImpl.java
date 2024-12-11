package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.User;
import com.AppArch.Project.Repository.UserRepo;

@Service
public class UserRepoServiceImpl implements UserRepoService{
	
	@Autowired
	private UserRepo UserRep;
	
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
}
