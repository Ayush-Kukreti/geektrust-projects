package com.example.geektrust;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Computation {

    Utils util = new Utils();

    public void execute(List<Station> inputTrainAStations,List<Station> inputTrainBStations) throws FileNotFoundException {

//        String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("/trainAData.txt")).getPath();
        InputStream in = Main.class.getResourceAsStream("/data.txt");
        InputStream inputStream = Main.class.getResourceAsStream("/trainAData.txt");
        HashMap<String, Integer> trainARouteDetails = util.generateBogies(inputStream);

        inputStream = Main.class.getResourceAsStream("/trainBData.txt");

        HashMap<String, Integer> trainBRouteDetails = util.generateBogies(inputStream);

        // only in the merger we have to the ordering and that too based on the distance
        util.prepareArrival(trainARouteDetails, trainBRouteDetails, inputTrainAStations);
        util.printArrival(Constants.TRAIN_A, inputTrainAStations);

        util.prepareArrival(trainBRouteDetails, trainARouteDetails, inputTrainBStations);
        util.printArrival(Constants.TRAIN_B, inputTrainBStations);

        //remove HYB
        util.removeStation(inputTrainAStations);
        util.removeStation(inputTrainBStations);
        if(inputTrainAStations.size() ==0 && inputTrainBStations.size()==0){
            System.out.println(Constants.JOURNEY_ENDED);
        } else{
            // sort the individual trains
            Collections.sort(inputTrainAStations, new SortByDistance());
            Collections.sort(inputTrainBStations, new SortByDistance());

            //TODO: prepare & print the departure
            util.prepareAndPrintDeparture(inputTrainAStations, inputTrainBStations);
        }
    }
}
