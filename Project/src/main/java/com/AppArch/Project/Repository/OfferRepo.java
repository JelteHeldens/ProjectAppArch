package com.AppArch.Project.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppArch.Project.Model.Offer;
import com.AppArch.Project.Model.OfferKey;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;


public interface OfferRepo extends JpaRepository<Offer, OfferKey> {
	
    @Query("SELECT o.taskId FROM Offer o WHERE o.email = :email")
    List<Task> findTasksByEmail(@Param("email") User email);
    
    @Query("SELECT o.email FROM Offer o WHERE o.taskId = :t")
    List<User> findUserByTask(@Param("t") Task t);
    
    @Query("SELECT count(o) FROM Offer o WHERE o.taskId = :t")
    int getNumberTaskByTaskId(@Param("t") Task t);
}