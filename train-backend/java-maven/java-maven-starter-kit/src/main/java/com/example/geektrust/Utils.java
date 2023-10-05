package com.example.geektrust;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public void removeStation(List<Station> inputTrainStations) {
        List<Station> storeStationsToRemove = new ArrayList<>();

        for (Station station : inputTrainStations) {
            String stationCode = station.getStationCode();
            if (stationCode.equals(Constants.HYDERABAD_STATION_CODE)) {
                storeStationsToRemove.add(station);
            }
        }
        // now deleting the HYB stations
        for (Station station : storeStationsToRemove) {
            inputTrainStations.remove(station);
        }
    }

    public void prepareAndPrintDeparture(List<Station> inputTrainAStations, List<Station> inputTrainBStations) {

        // prepare the departure
        List<String> departure = new ArrayList<>();
        int i = 0, j = 0;

        while (i < inputTrainAStations.size() && j < inputTrainBStations.size()) {
            Station trainAStation = inputTrainAStations.get(i);
            Station trainBStation = inputTrainBStations.get(j);

            if (trainAStation.getStationCode().equals(trainBStation.getStationCode())) {
                departure.add(trainAStation.getStationCode());
                departure.add(trainAStation.getStationCode());
                i++;
                j++;
            } else if (trainAStation.getRelativeDistance() >= trainBStation.getRelativeDistance()) {
                departure.add(trainAStation.getStationCode());
                i++;
            } else {
                departure.add(trainBStation.getStationCode());
                j++;
            }
        }
        while (i < inputTrainAStations.size()) {
            Station trainAStation = inputTrainAStations.get(i);
            departure.add(trainAStation.getStationCode());
            i++;
        }
        while (j < inputTrainBStations.size()) {
            Station trainBStation = inputTrainBStations.get(j);
            departure.add(trainBStation.getStationCode());
            j++;
        }

        // print the departure
        System.out.print(Constants.DEPARTURE + Constants.SPACE + Constants.TRAIN_AB);
        System.out.print(Constants.SPACE + Constants.ENGINE);
        System.out.print(Constants.SPACE + Constants.ENGINE);

        for (String stationCode : departure) {
            System.out.print(Constants.SPACE + stationCode);
        }
        System.out.println();
    }

    public void printArrival(String trainName, List<Station> inputTrainStations) {
        System.out.print(Constants.ARRIVAL + Constants.SPACE
                + trainName + Constants.SPACE + Constants.ENGINE);
        for (Station trainCode : inputTrainStations) {
            System.out.print(Constants.SPACE + trainCode.getStationCode());
        }
        System.out.println();
    }

    public void prepareArrival(HashMap<String, Integer> mainPassengers,
                                       HashMap<String, Integer> otherPassengers, List<Station> inputTrainStations) {
        // remove stations before the 1st common station(HYD), since they are already covered/passed
        final int distanceAlreadyTravelledByMainTrain = mainPassengers.get(Constants.HYDERABAD_STATION_CODE);
        final int distanceAlreadyTravelledByOtherTrain = otherPassengers.get(Constants.HYDERABAD_STATION_CODE);
        List<Station> storeStationsToRemove = new ArrayList<>();

        // collect what all stations need to be removed, directly removing it here is causing concurrent Modification exception
        for (Station station : inputTrainStations) {
            String stationCode = station.getStationCode();
            if (mainPassengers.containsKey(stationCode)) {
                if (distanceAlreadyTravelledByMainTrain > mainPassengers.get(stationCode)) {
                    storeStationsToRemove.add(station);
                } else {
                    // only updating the distance values for the stations which will be available after the arrival
                    // since other stations will get removed hence not updating their relative distance
                    // we will maintain the relative distance from HYD
                    int relativeDistance = mainPassengers.get(stationCode);
                    station.setRelativeDistance(relativeDistance - distanceAlreadyTravelledByMainTrain);
                }
            } else {
                int relativeDistance = otherPassengers.get(stationCode);
                station.setRelativeDistance(relativeDistance - distanceAlreadyTravelledByOtherTrain);
            }
        }
        // now deleting the stations for main train
        for (Station station : storeStationsToRemove) {
            inputTrainStations.remove(station);
        }

    }

    public HashMap<String, Integer> generateBogies(InputStream in) throws FileNotFoundException {

        Scanner sc = new Scanner(in);
        HashMap<String, Integer> trainDetails = new HashMap<>();

        while (sc.hasNextLine()) {
            final String[] station = sc.nextLine().split("-");
            final String stationCode = station[0];
            final Integer relativeStationDistance = Integer.valueOf(station[1]);
            trainDetails.put(stationCode, relativeStationDistance);
        }

        return trainDetails;
    }

    public List<Station> prepareStationInput(String input){
        String[] inputStations = input.split("\\s+");
        List<Station> inputTrainStations = new ArrayList<>();

        for (String inputStation : inputStations) {
            inputTrainStations.add(new Station(inputStation));
        }
        // remove train name
        inputTrainStations.remove(0);
        // remove engine value
        inputTrainStations.remove(0);

        return inputTrainStations;
    }
}
