package com.AppArch.Project.Model;

import java.io.Serializable;
import java.util.Objects;

public class OfferKey implements Serializable {

	private static final long serialVersionUID = 1L;
	private User email;
    private Task taskId;

    public OfferKey() {}

    public OfferKey(User email, Task taskId) {
        this.email = email;
        this.taskId = taskId;
    }

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

    // equals() and hashCode()
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferKey that = (OfferKey) o;
        return Objects.equals(email, that.email) && Objects.equals(taskId, that.taskId);
    }

    public int hashCode() {
        return Objects.hash(email, taskId);
    }
}

