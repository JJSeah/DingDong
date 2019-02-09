package com.example.jeremyjohn.dingdong.models;

public class Flag {
    private String BusNumber;
    private String BusStopNo;

    public Flag() {
    }

    public Flag(String busNumber, String busStopNo) {
        BusNumber = busNumber;
        BusStopNo = busStopNo;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }

    public String getBusStopNo() {
        return BusStopNo;
    }

    public void setBusStopNo(String busStopNo) {
        BusStopNo = busStopNo;
    }
}
