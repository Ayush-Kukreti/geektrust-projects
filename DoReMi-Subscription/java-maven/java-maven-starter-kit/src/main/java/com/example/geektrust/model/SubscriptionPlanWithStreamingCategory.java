package com.example.geektrust.model;

import com.example.geektrust.config.StreamingCategory;
import com.example.geektrust.config.SubscriptionType;
import com.example.geektrust.constants.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SubscriptionPlanWithStreamingCategory {
    private SubscriptionPlan subscriptionPlan;
    private StreamingCategory streamingCategory;

    public SubscriptionPlanWithStreamingCategory(String streamingCategory, String subscriptionPlanType){
        subscriptionPlan = new SubscriptionPlan();
        if(streamingCategory.equals(Constants.MUSIC)){
            setStreamingCategory(StreamingCategory.MUSIC);
            switch (subscriptionPlanType){
                case Constants.FREE:
                    createSubscriptionPlan(subscriptionPlanType,0,1);
                    break;
                case Constants.PERSONAL:
                    createSubscriptionPlan(subscriptionPlanType,100,1);
                    break;
                case Constants.PREMIUM:
                    createSubscriptionPlan(subscriptionPlanType,250,3);
            }
        }else if(streamingCategory.equals(Constants.VIDEO)){
            setStreamingCategory(StreamingCategory.VIDEO);
            switch (subscriptionPlanType){
                case Constants.FREE:
                    createSubscriptionPlan(subscriptionPlanType,0,1);
                    break;
                case Constants.PERSONAL:
                    createSubscriptionPlan(subscriptionPlanType,200,1);
                    break;
                case Constants.PREMIUM:
                    createSubscriptionPlan(subscriptionPlanType,500,3);
            }
        }else {
            setStreamingCategory(StreamingCategory.PODCAST);
            switch (subscriptionPlanType){
                case Constants.FREE:
                    createSubscriptionPlan(subscriptionPlanType,0,1);
                    break;
                case Constants.PERSONAL:
                    createSubscriptionPlan(subscriptionPlanType,100,1);
                    break;
                case Constants.PREMIUM:
                    createSubscriptionPlan(subscriptionPlanType,300,3);
            }
        }
    }
    private void createSubscriptionPlan( String subscriptionPlanType,
                                               int price, int period) {
        subscriptionPlan.setSubscriptionType(SubscriptionType.valueOf(subscriptionPlanType));
        subscriptionPlan.setPrice(price);
        subscriptionPlan.setPeriod(period);
    }

}
