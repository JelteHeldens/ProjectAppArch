package com.AppArch.Project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="CUSTOMERS")
public class User {
	
	@NotBlank
    @NotEmpty
	@Column(name="NAME")
	private String name;
	@NotBlank
    @NotEmpty
    @Id
	@Column(name="EMAIL")
	private String email;
	@NotBlank
    @NotEmpty
	@Column(name="PASSWD")
	private String passwd;
	@NotBlank
    @NotEmpty
	@Column(name="FUNCTIE")
	private String functie;
	@Column(name="ENABLED")
	private int enabled = 1;
	
	public User() {}
	public User(String name, String email, String passwd, String functie, int enabled) {
		this.name = name;
		this.email = email;
		this.passwd = passwd;
		this.functie = functie;
		this.enabled = enabled;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return functie;
	}
	public void setRole(String functie) {
		this.functie = functie;
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
}