package com.hsbc.probabilisticrandom;

public record NumAndProbabilityAndAccumulation(NumAndProbability numAndProbability,
                                               float probabilityAccumulation) implements ProbabilisticRandomGen {

    @Override
    public int nextFromSample() {
        throw new IllegalCallerException();
    }
}
