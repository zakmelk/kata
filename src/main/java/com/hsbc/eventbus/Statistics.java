package com.hsbc.eventbus;

public interface Statistics {
    double getMean();

    double getMode();

    double getPctile(int pctile);
}
