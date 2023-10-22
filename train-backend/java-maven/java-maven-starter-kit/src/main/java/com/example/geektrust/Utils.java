package com.example.geektrust;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public List<Station> removeStation(List<Station> inputTrainStations) {
        List<Station> stationsAfterRemoval = new ArrayList<>(inputTrainStations);

        for (Station station : inputTrainStations) {
            String stationCode = station.getStationCode();
            if (stationCode.equals(Constants.HYDERABAD_STATION_CODE)) {
                stationsAfterRemoval.remove(station);
            }
        }
        return stationsAfterRemoval;
    }

    public List<String> prepareDeparture(List<Station> inputTrainAStations, List<Station> inputTrainBStations) {

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

        return departure;
    }

    public String printDeparture(List<String> departure){

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.DEPARTURE).append(Constants.SPACE)
                .append(Constants.TRAIN_AB).append(Constants.SPACE)
                .append(Constants.ENGINE).append(Constants.SPACE)
                .append(Constants.ENGINE);

        for (String stationCode : departure) {
            sb.append(Constants.SPACE + stationCode);
        }

        return sb.toString();
    }

    public String printArrival(String trainName, List<Station> inputTrainStations) {

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.ARRIVAL).append(Constants.SPACE)
                        .append(trainName).append(Constants.SPACE)
                        .append(Constants.ENGINE);

        for (Station trainCode : inputTrainStations) {
            sb.append(Constants.SPACE).append(trainCode.getStationCode());
        }

        return sb.toString();
    }

    public void printToColsole(final String print){
        System.out.println(print);
    }
/**
 * mainPassengers: the main train actual route
 * otherPassengers: passengers those who have boarded the main train but will borad other train after the common point
 */
    public List<Station> prepareArrival(HashMap<String, Integer> mainPassengers,
                                       HashMap<String, Integer> otherPassengers, List<Station> inputTrainStations) {

        // remove the boggies which has been covered before the common station HYB
        List<Station> boggieAtArrival = new ArrayList<>(inputTrainStations);

        // remove stations before the 1st common station(HYB), since they are already covered/passed
        final int distanceAlreadyTravelledByMainTrain = mainPassengers.get(Constants.HYDERABAD_STATION_CODE);
        final int distanceAlreadyTravelledByOtherTrain = otherPassengers.get(Constants.HYDERABAD_STATION_CODE);
        List<Station> storeStationsToRemove = new ArrayList<>();

        // collect what all stations need to be removed, directly removing it here is causing concurrent Modification exception
        for (Station station : boggieAtArrival) {
            String stationCode = station.getStationCode();
            if (mainPassengers.containsKey(stationCode)) {
                if (distanceAlreadyTravelledByMainTrain > mainPassengers.get(stationCode)) {
                    storeStationsToRemove.add(station);
                } else {
                    // only updating the distance values for the stations which will be available after the arrival
                    // since other stations will get removed hence not updating their relative distance
                    // we will maintain the relative distance from HYD
                    int actualDistance = mainPassengers.get(stationCode);
                    station.setRelativeDistance(actualDistance - distanceAlreadyTravelledByMainTrain);
                }
            } else {
                int actualDistance = otherPassengers.get(stationCode);
                station.setRelativeDistance(actualDistance - distanceAlreadyTravelledByOtherTrain);
            }
        }
        // now deleting the stations for main train
        for (Station station : storeStationsToRemove) {
            boggieAtArrival.remove(station);
        }
        return boggieAtArrival;
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

    public static String[] getInput(InputStream fis) throws FileNotFoundException {

        // the file to be opened for reading
        Scanner sc = new Scanner(fis); // file to be scanned
        // input will always have 2 lines: trainA and trainB
        String input[] = new String[2];
        int count = 0;
        while (sc.hasNextLine()) {
            input[count++] = sc.nextLine();
        }
        return input;
    }
}
