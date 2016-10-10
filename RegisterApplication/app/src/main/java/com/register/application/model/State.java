package com.register.application.model;

import java.io.Serializable;
import java.util.List;

public class State implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2182779267806175646L;

	private Integer id;
	private String name;
	private String uf;
	private List<City> cities;

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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}