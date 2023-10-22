package com.example.geektrust.model;

import com.example.geektrust.config.SubscriptionType;
import lombok.Data;

@Data
public class SubscriptionPlan {

    private SubscriptionType subscriptionType;
    private int price;
    private int period;
}
