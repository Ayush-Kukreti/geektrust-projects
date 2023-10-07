package com.example.geektrust;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Computation {

    Utils utils = new Utils();

    public void execute(List<Station> inputTrainAStations,List<Station> inputTrainBStations) throws FileNotFoundException {

        InputStream inputStream = Main.class.getResourceAsStream("/trainAData.txt");
        HashMap<String, Integer> trainARouteDetails = utils.generateBogies(inputStream);

        inputStream = Main.class.getResourceAsStream("/trainBData.txt");
        HashMap<String, Integer> trainBRouteDetails = utils.generateBogies(inputStream);

        // only in the merger we have to do the ordering and that too based on the distance
        List<Station> boggieAtArrivalforTrainA = utils.prepareArrival(trainARouteDetails, trainBRouteDetails, inputTrainAStations);
        final String printArrivalForTrainA = utils.printArrival(Constants.TRAIN_A, boggieAtArrivalforTrainA);
        utils.printToColsole(printArrivalForTrainA);

        List<Station> boggieAtArrivalforTrainB = utils.prepareArrival(trainBRouteDetails, trainARouteDetails, inputTrainBStations);
        final String printArrivalForTrainB = utils.printArrival(Constants.TRAIN_B, boggieAtArrivalforTrainB);
        utils.printToColsole(printArrivalForTrainB);
        //remove HYB
        List<Station> stationsAfterRemovalForTrainA = utils.removeStation(boggieAtArrivalforTrainA);
        List<Station> stationsAfterRemovalForTrainB = utils.removeStation(boggieAtArrivalforTrainB);

        if(stationsAfterRemovalForTrainA.size() ==0 && stationsAfterRemovalForTrainB.size()==0){
            System.out.println(Constants.JOURNEY_ENDED);
        } else{
            // sort the individual trains
            Collections.sort(stationsAfterRemovalForTrainA, new SortByDistance());
            Collections.sort(stationsAfterRemovalForTrainB, new SortByDistance());

            //TODO: prepare & print the departure
            List<String> departure = utils.
                    prepareDeparture(stationsAfterRemovalForTrainA, stationsAfterRemovalForTrainB);
            final String printDeparture  = utils.printDeparture(departure);
            utils.printToColsole(printDeparture);
        }
    }
}
