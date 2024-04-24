package com.hsbc.probabilisticrandom;

public interface ProbabilisticRandomGen {
    int nextFromSample();

    record NumAndProbability(int number, float probabilityOfSample) {
    }
}
