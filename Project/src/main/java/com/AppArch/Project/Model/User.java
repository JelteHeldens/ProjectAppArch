package com.AppArch.Project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="customers")
public class User {
	@NotBlank(message = "email is required")
    @Id
	@Column(name="email")
	private String email;
	
	@NotBlank(message = "Name is required")
	@Column(name="name")
	private String name;

	@NotBlank(message = "password is required")
	@Column(name="passwd")
	private String passwd;
	
	@Column(name="enabled")
	private int enabled;
	
	public User() {}

	
	public User(String name, String email, String passwd, int enabled) {
		this.name = name;
		this.email = email;
		this.passwd = passwd;
		this.enabled = enabled;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPostitName() {
		if(name.length()>=18) {
			return name.substring(0,15)+"...";
		}
		return name;
	}
}
