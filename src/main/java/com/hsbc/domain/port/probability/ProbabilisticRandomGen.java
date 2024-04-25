package com.hsbc.domain.port.probability;

public interface ProbabilisticRandomGen {
    int nextFromSample();

    record NumAndProbability(int number, float probabilityOfSample) {
    }
}
