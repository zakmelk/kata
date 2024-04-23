package com.hsbc;

import lombok.Getter;

public record NumAndProbabilityAndAccumulation(NumAndProbability numAndProbability,
                                               float probabilityAccumulation) implements ProbabilisticRandomGen {

    @Override
    public int nextFromSample() {
        throw new IllegalCallerException();
    }
}
