package com.example.androidintents.fantaseav2;

public class UserQueue {
    String activity,agency_username,boat_name,capacity,date_queued,status,time_queued;

    public UserQueue(){

    }

    public UserQueue(String activity, String agency_username, String boat_name, String capacity, String date_queued, String status, String time_queued) {
        this.activity = activity;
        this.agency_username = agency_username;
        this.boat_name = boat_name;
        this.capacity = capacity;
        this.date_queued = date_queued;
        this.status = status;
        this.time_queued = time_queued;
    }

    public String getActivity() {
        return activity;
    }

    public String getAgency_username() {
        return agency_username;
    }

    public String getBoat_name() {
        return boat_name;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getDate_queued() {
        return date_queued;
    }

    public String getStatus() {
        return status;
    }

    public String getTime_queued() {
        return time_queued;
    }
}
