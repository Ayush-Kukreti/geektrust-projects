package com.example.geektrust.service;

import com.example.geektrust.config.StreamingCategory;
import com.example.geektrust.config.SubscriptionType;
import com.example.geektrust.constants.Constants;
import com.example.geektrust.model.Subscription;
import com.example.geektrust.model.SubscriptionPlan;
import com.example.geektrust.model.SubscriptionPlanWithStreamingCategory;
import com.example.geektrust.model.TopUp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {
    public static Subscription subscription = new Subscription();
    public static final boolean SUBSCRIPTION_ALREADY_ADDED_FOR_MUSIC = false;
    public static final boolean SUBSCRIPTION_ALREADY_ADDED_FOR_VIDEO = false;
    public static final boolean SUBSCRIPTION_ALREADY_ADDED_FOR_PODCAST = false;

    public static boolean SUBSCRIPTION_EXITS = false;
    public static void addSubscription(String streamingCategory, String subscriptionPlanType) {
        // validation: can be taken out somewhere else
        if(streamingCategory.equals(Constants.MUSIC) && SUBSCRIPTION_ALREADY_ADDED_FOR_MUSIC
                || streamingCategory.equals(Constants.VIDEO) && SUBSCRIPTION_ALREADY_ADDED_FOR_VIDEO
                || streamingCategory.equals(Constants.PODCAST) && SUBSCRIPTION_ALREADY_ADDED_FOR_PODCAST ){
            // todo: need to throw a custom exception, is valid add subscription operation or not.(validate and set the boolean there itself to true)
        //    throw new DuplicateCategoryException("DUPLICATE_CATEGORY");
        }
        // at least one subscription is there, important while topup is there.
        // since there can not be any topup if there is no subscription.
        SUBSCRIPTION_EXITS = true;

        SubscriptionPlanWithStreamingCategory subscriptionPlanWithStreamingCategory =
                new SubscriptionPlanWithStreamingCategory(streamingCategory, subscriptionPlanType);
        SubscriptionPlan subscriptionPlan =new SubscriptionPlan();
        List<SubscriptionPlanWithStreamingCategory> list = new ArrayList<>();

        subscription.subscriptionPlanWithStreamingCategories.add(subscriptionPlanWithStreamingCategory);
    }

    public void setSubscriptionDate(String startDate) throws ParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(startDate,format);
        subscription.setStartDate(date);
    }

    public void processInput(List<String> input) throws ParseException {

        for(String line : input){
            String []parts = line.split("\\s");
            switch (parts[0]){
                case Constants.START_SUBSCRIPTION:
                    setSubscriptionDate(parts[1]);
                    break;
                case Constants.ADD_SUBSCRIPTION:
                    addSubscription(parts[1], parts[2]);
                    break;
                case Constants.ADD_TOPUP:
                    addTopUp(parts[1], parts[2]);
                    break;
                case Constants.PRINT_RENEWAL_DETAILS:
                    break;
            }
        }
    }

    private void addTopUp(String topUpType, String period) {
        TopUp topUp = new TopUp(topUpType, Integer.parseInt(period));
        subscription.setTopUp(topUp);
    }

    public List<String> renewalReminder() {
        List<SubscriptionPlanWithStreamingCategory> subscriptionList = subscription.getSubscriptionPlanWithStreamingCategories();
        LocalDate startDate = subscription.getStartDate();
        final List<String> result = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for( SubscriptionPlanWithStreamingCategory spsc : subscriptionList){
            StreamingCategory streamingCategory = spsc.getStreamingCategory();
            int period = spsc.getSubscriptionPlan().getPeriod();
            LocalDate renewalDate = startDate.plusMonths(period).minusDays(10);
            StringBuilder sb = new StringBuilder();
            sb.append(streamingCategory.toString()).append(Constants.SPACE).append(renewalDate.format(format));
            result.add(sb.toString());
        }

        return result;
    }

    public int renewalAmount() {
        List<SubscriptionPlanWithStreamingCategory> subscriptionList = subscription.getSubscriptionPlanWithStreamingCategories();
        int amount = 0;
        for(SubscriptionPlanWithStreamingCategory spsc : subscriptionList){
            amount += spsc.getSubscriptionPlan().getPrice();
        }
        int pricePerMonth = subscription.getTopUp().getPricePerMonth();
        int periodOftopUp = subscription.getTopUp().getPeriod();
        amount += pricePerMonth * periodOftopUp;
        return amount;
    }
}
