package com.register.application.model;

import org.joda.time.LocalDate;

import java.io.Serializable;

public class BillsToReceive implements Serializable{

	private static final long serialVersionUID = 3619695852872734342L;
	
	private Integer id;
	private String creditor;
	private Integer numberParcels;
	private Integer numberReceivedParcels;
	private Integer numberParcelsToReceive;
	private String billsToReceiveStatus;	
	private String document;	
	private Double amountToReceive;
	private Double parcelAmount;
	private Double amountReceived;
	private Double totalToReceive;
	private LocalDate dueDate;
	private LocalDate receiptDate;

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

	public Integer getNumberReceivedParcels() {
		return numberReceivedParcels;
	}

	public void setNumberReceivedParcels(Integer numberReceivedParcels) {
		this.numberReceivedParcels = numberReceivedParcels;
	}

	public Integer getNumberParcelsToReceive() {
		return numberParcelsToReceive;
	}

	public void setNumberParcelsToReceive(Integer numberParcelsToReceive) {
		this.numberParcelsToReceive = numberParcelsToReceive;
	}

	public String getBillsToReceiveStatus() {
		return billsToReceiveStatus;
	}

	public void setBillsToReceiveStatus(String billsToReceiveStatus) {
		this.billsToReceiveStatus = billsToReceiveStatus;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Double getAmountToReceive() {
		return amountToReceive;
	}

	public void setAmountToReceive(Double amountToReceive) {
		this.amountToReceive = amountToReceive;
	}

	public Double getParcelAmount() {
		return parcelAmount;
	}

	public void setParcelAmount(Double parcelAmount) {
		this.parcelAmount = parcelAmount;
	}

	public Double getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(Double amountReceived) {
		this.amountReceived = amountReceived;
	}

	public Double getTotalToReceive() {
		return totalToReceive;
	}

	public void setTotalToReceive(Double totalToReceive) {
		this.totalToReceive = totalToReceive;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(LocalDate receiptDate) {
		this.receiptDate = receiptDate;
	}
}
