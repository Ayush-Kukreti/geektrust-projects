package com.example.geektrust.model;

import com.example.geektrust.config.TopUpType;
import com.example.geektrust.constants.Constants;
import lombok.Data;

@Data
public class TopUp {

    private TopUpType topUpType;
    private int numberOfDevice;
    private int pricePerMonth;
    private int period;

    public TopUp(String topUpType, int period){
        this.period = period; // since this is common for both case.

        switch (topUpType){
            case Constants.FOUR_DEVICE:
                this.numberOfDevice = 4;
                this.pricePerMonth = 50;
                break;
            case Constants.TEN_DEVICE:
                this.numberOfDevice = 10;
                this.pricePerMonth = 100;
        }
    }
}
