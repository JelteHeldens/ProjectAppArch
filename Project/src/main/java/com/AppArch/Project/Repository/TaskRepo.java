package com.AppArch.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AppArch.Project.Model.Task;


public interface TaskRepo extends JpaRepository<Task, Integer> {

}
