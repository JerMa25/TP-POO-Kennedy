package com.example.myproperty;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private Date DOB;
    private String town;
    private String quarter;
    private String email;
    private String phoneNumber;
    private String gender;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDOB() {
        return DOB;
    }

    public String getTown() {
        return town;
    }

    public String getQuarter() {
        return quarter;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
