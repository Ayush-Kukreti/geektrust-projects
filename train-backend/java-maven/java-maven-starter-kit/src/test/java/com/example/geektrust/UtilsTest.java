package com.example.geektrust;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class UtilsTest {
    static Utils utils;
    @BeforeAll
    public static void init(){
         utils = new Utils();
    }
    @Test
    public void testPrintArrival(){


//        utils.printArrival();
    }

    @Test
    public void testPrepareStationInput(){
        final String input= "TRAIN_A ENGINE NDL NDL KRN GHY SLM NJP NGP";

        List<Station> expetecdTrainStations = Arrays.asList(new Station("NDL"), new Station("NDL"), new Station("KRN"),
                new Station("GHY"), new Station("SLM"), new Station("NJP"), new Station("NGP"));

        List<Station> actualTrainStations = utils.prepareStationInput(input);
        Assertions.assertEquals(actualTrainStations,expetecdTrainStations);
    }
}
