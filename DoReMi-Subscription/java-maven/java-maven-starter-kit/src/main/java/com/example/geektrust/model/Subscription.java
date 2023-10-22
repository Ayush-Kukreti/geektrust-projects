package com.example.geektrust.model;


import com.example.geektrust.constants.Constants;
import com.example.geektrust.config.StreamingCategory;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Subscription {
    public List<SubscriptionPlanWithStreamingCategory> subscriptionPlanWithStreamingCategories = new ArrayList<>();
    public LocalDate startDate;
    private TopUp topUp;
}
