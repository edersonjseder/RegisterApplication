package com.register.application.model;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 333444551L;


	private Integer id;
	private String address;
	private String neighborhood;
	private City city;
	private List<Clients> clients;
	private List<Providers> providers;
	private List<Employees> employees;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Clients> getClients() {
		return clients;
	}

	public void setClients(List<Clients> clients) {
		this.clients = clients;
	}

	public List<Providers> getProviders() {
		return providers;
	}

	public void setProviders(List<Providers> providers) {
		this.providers = providers;
	}

	public List<Employees> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}
}
