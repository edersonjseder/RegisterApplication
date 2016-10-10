package com.register.application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ederson.js on 28/03/2016.
 */
public class Clients implements Serializable {

    private Integer id;
    private String name;
    private String cpf;
    private Character gender;
    private String phoneNumber;
    private String email;
    private List<Address> address = new ArrayList<>();
    private List<Schedules> schedules;
    private List<CustomerService> customerServices;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Schedules> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedules> schedules) {
        this.schedules = schedules;
    }

    public List<CustomerService> getCustomerServices() {
        return customerServices;
    }

    public void setCustomerServices(List<CustomerService> customerServices) {
        this.customerServices = customerServices;
    }
}
