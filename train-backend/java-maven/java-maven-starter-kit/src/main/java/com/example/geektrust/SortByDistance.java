package com.example.geektrust;

import java.util.Comparator;

public class SortByDistance implements Comparator<Station> {
    @Override
    public int compare(Station a, Station b) {
        return b.getRelativeDistance() -a.getRelativeDistance();
    }
}
