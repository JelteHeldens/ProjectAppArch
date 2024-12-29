package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import com.AppArch.Project.Model.User;
import com.AppArch.Project.Model.UserAuthorization;

public interface UserRepoService {
	public void addUser(User u);
	public void addAuth(UserAuthorization ua);
	public List<User> getusers();
	public Optional<User> getUserById(String e);
	public String getCurrentUser();
	public void updateUser(String mail, String NEWname);
	public boolean existsByEmail(String email);
}
