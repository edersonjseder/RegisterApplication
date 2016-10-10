package com.register.application.model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;

public class Schedules implements Serializable{

	private static final long serialVersionUID = -4588376288311929667L;
	
	private Integer id;
	private LocalDate schedulingDate;
	private String schedulingTime;
	private String schedulingStatus;
	private String description;
	private List<CustomerService> customerServices;
	private List<Employees> employees;
	private List<Clients> clients;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getSchedulingDate() {
		return schedulingDate;
	}

	public void setSchedulingDate(LocalDate schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	public String getSchedulingTime() {
		return schedulingTime;
	}

	public void setSchedulingTime(String schedulingTime) {
		this.schedulingTime = schedulingTime;
	}

	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CustomerService> getCustomerServices() {
		return customerServices;
	}

	public void setCustomerServices(List<CustomerService> customerServices) {
		this.customerServices = customerServices;
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
