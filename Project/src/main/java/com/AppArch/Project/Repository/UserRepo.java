package com.AppArch.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AppArch.Project.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

}
