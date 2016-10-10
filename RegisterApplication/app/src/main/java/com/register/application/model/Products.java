package com.register.application.model;

import org.joda.time.LocalDate;

import java.io.Serializable;

public class Products implements Serializable{

	private static final long serialVersionUID = 5800699077907979919L;
	
	private Integer id;
	private String name;
	private String description;
	private String category;
	private Long quantity;
	private LocalDate registerDate;
	private LocalDate validateDate;
	private Double price;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public LocalDate getValidateDate() {
		return validateDate;
	}

	public void setValidateDate(LocalDate validateDate) {
		this.validateDate = validateDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
