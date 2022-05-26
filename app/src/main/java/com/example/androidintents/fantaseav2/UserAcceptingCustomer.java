package com.example.androidintents.fantaseav2;

public class UserAcceptingCustomer {
    String date_booked,date_scheduled,agency_name,destination_name,activities,destination_province,pump_boat_name,seating_capacity,payID,ticketStatus,customer_name;
    int price;
    public UserAcceptingCustomer(){

    }

    public UserAcceptingCustomer(String date_booked, String date_scheduled, String agency_name, String destination_name, String activities, String destination_province, String pump_boat_name, String seating_capacity, String payID, String ticketStatus, String customer_name, int price) {
        this.date_booked = date_booked;
        this.date_scheduled = date_scheduled;
        this.agency_name = agency_name;
        this.destination_name = destination_name;
        this.activities = activities;
        this.destination_province = destination_province;
        this.pump_boat_name = pump_boat_name;
        this.seating_capacity = seating_capacity;
        this.payID = payID;
        this.ticketStatus = ticketStatus;
        this.customer_name = customer_name;
        this.price = price;
    }

    public String getDate_booked() {
        return date_booked;
    }

    public void setDate_booked(String date_booked) {
        this.date_booked = date_booked;
    }

    public String getDate_scheduled() {
        return date_scheduled;
    }

    public void setDate_scheduled(String date_scheduled) {
        this.date_scheduled = date_scheduled;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getDestination_province() {
        return destination_province;
    }

    public void setDestination_province(String destination_province) {
        this.destination_province = destination_province;
    }

    public String getPump_boat_name() {
        return pump_boat_name;
    }

    public void setPump_boat_name(String pump_boat_name) {
        this.pump_boat_name = pump_boat_name;
    }

    public String getSeating_capacity() {
        return seating_capacity;
    }

    public void setSeating_capacity(String seating_capacity) {
        this.seating_capacity = seating_capacity;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
