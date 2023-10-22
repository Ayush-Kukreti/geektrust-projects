package com.example.geektrust;

import com.example.geektrust.service.Service;
import com.example.geektrust.utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public class Main {
    static Utils utils = new Utils();
    static Service service = new Service();

    public static void main(String[] args) throws FileNotFoundException, ParseException {

        FileInputStream fis = new FileInputStream(args[0]);
        List<String> input = utils.getInput(fis);
        service.processInput(input);

        List<String> reminders = service.renewalReminder();
        final String prepareReminderOutput = utils.prepareReminderOutput(reminders);
        utils.printToConsole(prepareReminderOutput);

        int amount = service.renewalAmount();
        final String prepareAmountOutput = utils.prepareAmountOutput(amount);
        utils.printToConsole(prepareAmountOutput);

    }
}
