package com.example.geektrust;



public class Station {

    public String stationCode;
    public Integer relativeDistance;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public void setRelativeDistance(Integer relativeDistance) {
        this.relativeDistance = relativeDistance;
    }

    public  Integer getRelativeDistance() {
        return relativeDistance;
    }

    public Station(String stationCode) {
        this(stationCode,0);
    }

    public Station(String stationCode, Integer relativeDistance) {
        this.stationCode = stationCode;
        this.relativeDistance = relativeDistance;
    }

}
