package com.AppArch.Project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="task")
public class Task {
	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotBlank
	@Column(name="title")
	private String title;
	
	@NotBlank
	@Column(name="description")
	private String description;
	
	@NotNull
	@Column(name="price")
	private float price;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="owner")
	private User owner;
	
	@NotNull
	@Min(value=0, message="Rating moet tussen 0 en 5 liggen")
	@Max(value=5, message="Rating moet tussen 0 en 5 liggen")
	@Column(name = "rating")
	private Integer rating;
	
	@NotNull
	@Column(name="status")
	private State status;
	
	@ManyToOne
	@JoinColumn(name="executor")
	private User executor;
	
	public Task() {} //imported for the get request
	
	public Task(String title, String description, float price, User owner) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.owner = owner;
		//Bij aanmaken van job is de status van de job 'BESCHIKBAAR', zie enumeratie 'State'
		//		De lijst geïnteresseerden is leeg, en er is nog geen klusjesman toegekend.
		this.status = State.BESCHIKBAAR;
		this.rating = 0;
		this.executor = null;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}	
	public int getId() {
		return this.id;
	}
	public State getStatus() {
		return status;
	}
	public void setStatus(State status) {
		this.status = status;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public User getExecutor() {
		return executor;
	}
	public void setExecutor(User e) {
		this.executor = e;
	}
}
