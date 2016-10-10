package com.register.application.model;

import org.joda.time.LocalDate;

import java.io.Serializable;

public class BillsToPay implements Serializable{

	private static final long serialVersionUID = 3619695852872734342L;

	private Integer id;
	private String creditor;
	private Integer numberParcels;
	private Integer numberPaidParcels;
	private Integer numberParcelsToPay;
	private String billsToPayStatus;	
	private String document;	
	private Double amountToPay;
	private Double parcelAmount;
	private Double amountPaid;
	private Double totalToPay;
	private LocalDate dueDate;
	private LocalDate paymentDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public Integer getNumberParcels() {
		return numberParcels;
	}

	public void setNumberParcels(Integer numberParcels) {
		this.numberParcels = numberParcels;
	}

	public Integer getNumberPaidParcels() {
		return numberPaidParcels;
	}

	public void setNumberPaidParcels(Integer numberPaidParcels) {
		this.numberPaidParcels = numberPaidParcels;
	}

	public Integer getNumberParcelsToPay() {
		return numberParcelsToPay;
	}

	public void setNumberParcelsToPay(Integer numberParcelsToPay) {
		this.numberParcelsToPay = numberParcelsToPay;
	}

	public String getBillsToPayStatus() {
		return billsToPayStatus;
	}

	public void setBillsToPayStatus(String billsToPayStatus) {
		this.billsToPayStatus = billsToPayStatus;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(Double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public Double getParcelAmount() {
		return parcelAmount;
	}

	public void setParcelAmount(Double parcelAmount) {
		this.parcelAmount = parcelAmount;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Double getTotalToPay() {
		return totalToPay;
	}

	public void setTotalToPay(Double totalToPay) {
		this.totalToPay = totalToPay;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
}
