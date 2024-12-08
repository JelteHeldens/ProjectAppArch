package com.AppArch.Project.Service;

import java.util.List;

import com.AppArch.Project.Model.User;

public interface UserRepoService {
	public void addUser(User u);
	public List<User> getusers();
}
