package com.AppArch.Project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AppArch.Project.Model.State;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;

import jakarta.transaction.Transactional;


public interface TaskRepo extends JpaRepository<Task, Integer> {	
	@Query("SELECT t FROM Task t WHERE t.owner = :u")
    List<Task> findByUser(User u);
	
	@Query("SELECT t FROM Task t WHERE t.owner = :u AND t.status = :s")
    List<Task> findByUserAndState(User u, State s);
	
	@Query("SELECT t FROM Task t WHERE t.owner = :u AND t.status < :s")
    List<Task> findByUserAndStateLessThan(User u, State s);
	
	@Query("SELECT t FROM Task t WHERE t.owner = :u AND t.status < 4")
    List<Task> findByUserAndActive(User u);
	
	@Query("SELECT t FROM Task t WHERE t.owner = :u AND t.status >= 4")
    List<Task> findByUserAndDone(User u);
	
	@Query("SELECT t FROM Task t WHERE t.status < 2")
	List<Task> findOpenTasks();
	
	@Query("SELECT t FROM Task t WHERE t.status >= 2")
	List<Task> findClosedTasks();
	
    
	@Modifying //Nodig voor UPDATE en DELETE queries
    @Transactional //Zorgt dat query verloopt als transactie (ACID principes)
	@Query("UPDATE Task t SET t.title = :title, t.description = :description, t.price = :price WHERE t.id = :id")
    int updateTask(@Param("id") int id, 
                          @Param("title") String title, 
                          @Param("description") String description, 
                          @Param("price") float price);
	
	@Modifying
	@Transactional
	@Query("UPDATE Task t SET t.status = :state WHERE t.id = :id")
	int changeState(@Param("id") int id, @Param("state") State state);
	
	@Modifying
	@Transactional
	@Query("UPDATE Task t SET t.status = 2, t.executor = :executor WHERE t.id = :id")
	int addExecutor(@Param("id") int id, @Param("executor") User executor);
	
	@Modifying
	@Transactional
	@Query("UPDATE Task t SET t.rating = :rating WHERE t.id = :id")
	public void reviewTask(@Param("id") int id, @Param("rating") float rating);
	
	@Query("select AVG(t.rating) from Task t where t.executor= :executor and status = 4")
	float getAverageRating(@Param("executor") User executor);
	
	@Modifying
	@Transactional
	@Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
	public void updateStatus(@Param("id") int id, @Param("status") State status);
	
}
