package com.example.androidintents.fantaseav2;

public class queueGetInfo {
    String agencyname,username,seatingcapacity;

    public queueGetInfo(){

    }

    public queueGetInfo(String agencyname, String username, String seatingcapacity) {
        this.agencyname = agencyname;
        this.username = username;
        this.seatingcapacity = seatingcapacity;
    }

    public String getAgencyname() {
        return agencyname;
    }

    public String getUsername() {
        return username;
    }

    public String getSeatingcapacity() {
        return seatingcapacity;
    }
}
