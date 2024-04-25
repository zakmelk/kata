package com.hsbc.domain.model.event;

public interface Statistics {
    double getMean();

    double getMode();

    double getPctile(int pctile);
}
