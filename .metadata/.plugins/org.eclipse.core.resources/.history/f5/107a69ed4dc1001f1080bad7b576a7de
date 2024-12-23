package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.AppArch.Project.Model.Offer;
import com.AppArch.Project.Model.OfferKey;
import com.AppArch.Project.Model.State;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;

public interface OfferRepoService {
	 public Optional<Offer> getOffer(User email, Task taskId);
	 public void addOffer(Offer o);
	 List<Task> findTasksByEmail(User email);
	 List<User> findUserByTask(Task t);
	 List<Offer> getAll();
	 public void deleteById(OfferKey k);
	 public int getNumberTaskByTaskId(Task t);
	 


}
