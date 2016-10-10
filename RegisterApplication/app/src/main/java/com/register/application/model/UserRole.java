package com.register.application.model;

import java.io.Serializable;
import java.util.List;

public class UserRole implements Serializable {

	private static final long serialVersionUID = 7623387794702861701L;
	
	private Integer id;
	private String login;
	private String role;
	private List<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
