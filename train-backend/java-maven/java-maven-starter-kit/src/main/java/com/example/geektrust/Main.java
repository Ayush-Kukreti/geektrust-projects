package com.example.geektrust;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    static Computation computation = new Computation();
    static Utils utils = new Utils();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = null;
        // the file to be opened for reading
        FileInputStream fis = new FileInputStream(args[0]);
        sc = new Scanner(fis); // file to be scanned

        // input will always have 2 lines: trainA and trainB
        String input[] = new String[2];
        int count = 0;
        while (sc.hasNextLine()) {
            input[count++] = sc.nextLine();
        }

        List<Station> inputTrainAStations = utils.prepareStationInput(input[0]);
        List<Station> inputTrainBStations = utils.prepareStationInput(input[1]);

        computation.execute(inputTrainAStations,inputTrainBStations);
    }

}
