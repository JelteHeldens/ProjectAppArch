package com.AppArch.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AppArch.Project.Model.OfferKey;
import com.AppArch.Project.Model.Task;
import com.AppArch.Project.Model.User;
import com.AppArch.Project.Repository.OfferRepo;
import com.AppArch.Project.Model.Offer;

@Service
public class OfferRepoServiceImpl implements OfferRepoService{
    @Autowired
    private OfferRepo offerRepo;
    
    public Optional<Offer> getOffer(User email, Task taskId) {
        OfferKey key = new OfferKey(email, taskId);
        return offerRepo.findById(key);
    }

	public void addOffer(Offer o) {
		offerRepo.save(o);
	}


	public List<Task> findTasksByEmail(User email) {
		return offerRepo.findTasksByEmail(email);
	}
	public List<User> findUserByTask(Task t){
		return offerRepo.findUserByTask(t);
	}

}
