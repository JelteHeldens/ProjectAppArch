package com.AppArch.Project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.User;
import com.AppArch.Project.Repository.UserRepo;

@Service
public class UserRepoServiceImpl implements UserRepoService{
	
	@Autowired
	private UserRepo UserRep;
	
	public void addUser(User u) {
		System.out.println(u);
		UserRep.save(u);
	}
	
	public List<User> getusers(){
		return UserRep.findAll();
	}
}
