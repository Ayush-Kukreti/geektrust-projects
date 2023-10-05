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

    // need to add this for JUnit assertions
    // when we are using a user defined List we need to override the equals method
    @Override
    public boolean equals(Object obj) {
        Station station =(Station) obj;
        boolean status = false;
        if(this.stationCode.equals(station.stationCode)){
            status = true;
        }
        return status;
    }
}
