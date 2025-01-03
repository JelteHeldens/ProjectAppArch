package com.AppArch.Project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="authorities")
public class UserAuthorization {
	@NotNull
    @Id
    @Column(name = "email")
    private String email;
	
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;
	
	@NotBlank
    @Column(name = "authority", nullable = false)
    private String role;
	
	public UserAuthorization() {}
	
	public UserAuthorization(String email, String role) {
		this.email = email;
		this.role = "ROLE_"+role;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
