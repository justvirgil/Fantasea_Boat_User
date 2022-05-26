package com.example.androidintents.fantaseav2;

public class Information {

    String username,agencyname,seatingcapacity,firstname,lastname,phonenumber,email,password,address;
    public static String globalUser,globalID,globalAgencyName;

    public Information() {

    }
    //constructor
    public Information(String username, String agencyname, String seatingcapacity, String firstname, String lastname, String phonenumber, String email, String password, String address) {
        this.username = username;
        this.agencyname = agencyname;
        this.seatingcapacity = seatingcapacity;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    //getter setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getSeatingcapacity() {
        return seatingcapacity;
    }

    public void setSeatingcapacity(String seatingcapacity) {
        this.seatingcapacity = seatingcapacity;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
