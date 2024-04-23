package com.hsbc;

import lombok.Getter;

public interface ProbabilisticRandomGen {
    int nextFromSample();
    record NumAndProbability(int number, float probabilityOfSample) {
    }
}
