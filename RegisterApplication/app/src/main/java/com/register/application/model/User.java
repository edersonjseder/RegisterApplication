package com.register.application.model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 614240595617021758L;
	
	private Integer id;
	private String login;
	private String senha;
	private UserRole userRole;
	private Employees employee;

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}
}
