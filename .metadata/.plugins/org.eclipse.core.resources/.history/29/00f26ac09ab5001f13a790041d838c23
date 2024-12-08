package com.AppArch.Project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.User;
import com.AppArch.Project.Repository.UserRepo;

@Service
public class UserRepoServiceImpl {
	
	@Autowired
	private UserRepo UserRep;
	
	public void addUser(User u) {
		UserRep.save(u);
	}
}
