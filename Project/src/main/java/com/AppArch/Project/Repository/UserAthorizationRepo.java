package com.AppArch.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AppArch.Project.Model.UserAuthorization;

public interface UserAthorizationRepo extends JpaRepository<UserAuthorization, String>{

}