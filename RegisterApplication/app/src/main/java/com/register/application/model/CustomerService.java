package com.register.application.model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;
public class CustomerService implements Serializable{
	
	private static final long serialVersionUID = -2375372180919316210L;
	
	private Integer id;
	private String name;
	private LocalDate serviceDate;
	private String serviceTime;
	private String description;
	private String serviceStatus;
	private Double price;
	private List<Schedules> schedules;
	private List<Employees> employees;
	private List<Clients> clients;

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

	public LocalDate getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(LocalDate serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Schedules> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedules> schedules) {
		this.schedules = schedules;
	}

	public List<Employees> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}

	public List<Clients> getClients() {
		return clients;
	}

	public void setClients(List<Clients> clients) {
		this.clients = clients;
	}
}
