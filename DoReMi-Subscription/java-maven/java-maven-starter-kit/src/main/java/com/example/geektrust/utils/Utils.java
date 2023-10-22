package com.example.geektrust.utils;

import com.example.geektrust.constants.Constants;
import com.example.geektrust.service.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public Service service = new Service();
    public List<String> getInput(InputStream fis) {

        Scanner sc = new Scanner(fis); // file to be scanned
        List<String> input = new ArrayList<>();
        int count = 0;
        while (sc.hasNextLine()) {
            input.add(sc.nextLine());
        }
        return input;
    }

    public void printToConsole(String print) {
        System.out.print(print);
    }

    public String prepareReminderOutput(List<String> remiders) {
        StringBuilder sb = new StringBuilder();

        for(String s : remiders){
            sb.append(Constants.RENEWAL_REMINDER).append(Constants.SPACE)
                    .append(s).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String prepareAmountOutput(int amount) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.RENEWAL_AMOUNT).append(Constants.SPACE).append(amount);
        return sb.toString();
    }
}
