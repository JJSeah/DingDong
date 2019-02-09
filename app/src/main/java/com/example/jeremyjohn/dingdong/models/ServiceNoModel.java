package com.example.jeremyjohn.dingdong.models;

import java.util.ArrayList;
import java.util.List;

public class ServiceNoModel {
    private int id;
    private String BusStopCode;
    private String RoadName;
    private String Description;
    private String latitude;
    private String longitude;
    private String pass;

    public ServiceNoModel(int id, String BusStopCode, String Description, String latitude, String longitude, String pass) {
        this.id = id;
        this.BusStopCode = BusStopCode;
        this.Description = Description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pass = pass;
    }

    public void ServiceNoModel(String BusStopCode, String RoadName, String Description, String latitude, String longitude) {
        this.BusStopCode = BusStopCode;
        this.RoadName = RoadName;
        this.Description = Description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getBusStopCode() {return BusStopCode; }
    public void setBusStopCode(String BusStopCode) { this.BusStopCode = BusStopCode; }
    public String getRoadName() {return RoadName; }
    public void setRoadName(String RoadName) { this.RoadName = RoadName; }
    public String getDescription() {return Description; }
    public void setDescription(String Description) { this.Description = Description; }
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }




}
