package com.hsbc.domain.model.probability;

import com.hsbc.domain.port.probability.ProbabilisticRandomGen;

public record NumAndProbabilityAndAccumulation(ProbabilisticRandomGen.NumAndProbability numAndProbability,
                                               float probabilityAccumulation) implements ProbabilisticRandomGen {

    @Override
    public int nextFromSample() {
        throw new IllegalCallerException();
    }
}
