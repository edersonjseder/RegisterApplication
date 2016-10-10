package com.register.application.model;

import java.io.Serializable;
import java.util.List;

public class City implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1046035414546407898L;

	private Integer id;
	
	private String name;
	private State state;
	private List<Address> addresses;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}
