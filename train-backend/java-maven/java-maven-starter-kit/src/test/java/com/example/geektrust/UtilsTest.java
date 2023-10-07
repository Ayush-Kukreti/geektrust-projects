package com.example.geektrust;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class UtilsTest {
    static Utils utils;
    static List<Station> trainA;
    static List<Station> trainB;
    final String input= "TRAIN_A ENGINE NDL NDL KRN GHY SLM";
    static HashMap<String, Integer> map1;
    @BeforeAll
    public static void init(){
         utils = new Utils();
        map1 = new HashMap<>();
        map1.put("SLM",350);
        map1.put("BLR",550);
        map1.put("KRN",900);
        map1.put("HYB",1200);
        map1.put("NGP",1600);

        trainA = Arrays.asList(new Station("NDL",2700), new Station("HYB",1200),
                new Station("KRN",900), new Station("GHY",4700), new Station("SLM",350),
                new Station("HYB",1200), new Station("NGP",1600));

//        trainB = Arrays.asList(new Station("NDL"), new Station("HYB"), new Station("KRN"),
//                new Station("GHY"), new Station("SLM"), new Station("HYB"), new Station("NGP"));
    }

    @Test
    public void testPrepareStationInput(){

        List<Station> expetecdTrainStations = Arrays.asList(new Station("NDL"), new Station("NDL"),
                new Station("KRN"),new Station("GHY"), new Station("SLM"));

        List<Station> actualTrainStations = utils.prepareStationInput(input);
        Assertions.assertEquals(expetecdTrainStations,actualTrainStations);
    }

    @Test
    public void testRemoveStation(){

        List<Station> expected = Arrays.asList(new Station("NDL"), new Station("KRN"),
                new Station("GHY"), new Station("SLM"), new Station("NGP"));

        List<Station> actual = utils.removeStation(trainA);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGenerateBogies() throws FileNotFoundException {

        InputStream inputStream = UtilsTest.class.getResourceAsStream("/testData1.txt");

        HashMap<String, Integer> actual = utils.generateBogies(inputStream);

        Assertions.assertEquals(map1, actual);

    }
    @Test
    public void testPrepareArrival() throws FileNotFoundException {

        List<Station> expected = Arrays.asList(new Station("NDL"), new Station("HYB"),
                new Station("GHY"), new Station("HYB"), new Station("NGP"));

        InputStream inputStream = UtilsTest.class.getResourceAsStream("/trainAData.txt");
        HashMap<String, Integer> trainARouteDetails = utils.generateBogies(inputStream);

        inputStream = UtilsTest.class.getResourceAsStream("/trainBData.txt");
        HashMap<String, Integer> trainBRouteDetails = utils.generateBogies(inputStream);

        List<Station> actual = utils.prepareArrival(trainARouteDetails, trainBRouteDetails, trainA);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void testPrepareDeparture(){

        //case 1
        List<Station> train1 = Arrays.asList(new Station("NDL",2000), new Station("NDL",2000),
                new Station("KRN",1500),new Station("BPL",800));

        List<Station> train2 = Arrays.asList(new Station("GHY",2200), new Station("NDL",2000),
                new Station("ITJ",1400),new Station("SRR",1100),new Station("HYB",700));

        List<String> expected = Arrays.asList("GHY","NDL","NDL","NDL","KRN","ITJ","SRR","BPL","HYB");

        List<String> actual = utils.prepareDeparture(train1, train2);

        Assertions.assertEquals(expected, actual);

        // case 2
        train1 = Arrays.asList(new Station("NDL",2000), new Station("NDL",2000),
                new Station("KRN",1500),new Station("BPL",800));

        train2 = Arrays.asList(new Station("GHY",2200), new Station("NDL",2000),
                new Station("ITJ",1400),new Station("SRR",1100));

        expected = Arrays.asList("GHY","NDL","NDL","NDL","KRN","ITJ","SRR","BPL");

       actual = utils.prepareDeparture(train1, train2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testPrintDeparture(){
        List<String> departure = Arrays.asList("GHY","NDL","NDL","KRN","ITJ","SRR","BPL");

        final String expected = "DEPARTURE TRAIN_AB ENGINE ENGINE GHY NDL NDL KRN ITJ SRR BPL";
        final String actual = utils.printDeparture(departure);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void testPrintArrival() {
        final String trainName = "TRAIN_A";
        final String expected = "ARRIVAL TRAIN_A ENGINE NDL HYB KRN GHY SLM HYB NGP";
        final String actual = utils.printArrival(trainName,trainA);

        Assertions.assertEquals(expected,actual);
    }
}
