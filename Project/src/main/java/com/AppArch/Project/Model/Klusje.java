package com.AppArch.Project.Model;

import java.util.ArrayList;

public class Klusje {
	private String title;
	private String description;
	private float price;
	private User owner;
	private ArrayList<User> offers;
	
	private State status;
	private User assigned;
	
	public Klusje(String title, String description, float price, User owner) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.owner = owner;
		//Bij aanmaken van job is de status van de job 'BESCHIKBAAR', zie enumeratie 'State'
		//		De lijst geïnteresseerden is leeg, en er is nog geen klusjesman toegekend.
		this.status = State.BESCHIKBAAR;
		this.offers = new ArrayList<User>();
		this.assigned = new User();
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
	public ArrayList<User> getOffers() {
		return offers;
	}
	public void setOffers(ArrayList<User> offers) {
		this.offers = offers;
	}
	public State getStatus() {
		return status;
	}
	public void setStatus(State status) {
		this.status = status;
	}
	public User getAssigned() {
		return assigned;
	}
	public void setAssigned(User assigned) {
		this.assigned = assigned;
	}
}
