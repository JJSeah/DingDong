package com.example.jeremyjohn.dingdong.models;

public class RouteModel {

    private int id;
    private String ServiceNo;
    private String Operator;
    private String Direction;
    private String StopSequence;
    private String BusStopCode;
    private String Distance;

    public RouteModel() {

    }

    public RouteModel(int id, String busStopCode, String direction, String stopSequence, String serviceNo) {
        this.id = id;
        ServiceNo = serviceNo;
        Direction = direction;
        StopSequence = stopSequence;
        BusStopCode = busStopCode;
    }

    public String getServiceNo() {
        return ServiceNo;
    }

    public void setServiceNo(String serviceNo) {
        ServiceNo = serviceNo;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(String stopSequence) {
        StopSequence = stopSequence;
    }

    public String getBusStopCode() {
        return BusStopCode;
    }

    public void setBusStopCode(String busStopCode) {
        BusStopCode = busStopCode;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
