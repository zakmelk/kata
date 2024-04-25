package com.hsbc.adapter.statistic;


import com.hsbc.domain.model.event.Statistics;

import java.util.concurrent.BlockingQueue;

public class StatisticsImpl implements Statistics {
    private final double mean;
    private final double mode;
    private final double pctile;

    public StatisticsImpl(BlockingQueue<Integer> measurements) {
        mean = measurements.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
        mode = measurements.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
        pctile = measurements.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
    }

    @Override
    public double getMean() {
        return mean;
    }

    @Override
    public double getMode() {
        return mode;
    }

    @Override
    public double getPctile(int pctile) {
        return pctile;
    }
}
