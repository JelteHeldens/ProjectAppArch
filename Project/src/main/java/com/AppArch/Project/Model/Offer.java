package com.AppArch.Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(OfferKey.class)
@Table(name="offers")
public class Offer {

    @Id
    @ManyToOne
    @JoinColumn(name = "email")
    private User email;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskId;

    // Default constructor
    public Offer() {}

    public Offer(User email, Task taskId) {
        this.email = email;
        this.taskId = taskId;
    }

    // Getters and setters
    public User getEmail() {
        return email;
    }

    public void setEmail(User email) {
        this.email = email;
    }

    public Task getTaskId() {
        return taskId;
    }

    public void setTaskId(Task taskId) {
        this.taskId = taskId;
    }

 
}