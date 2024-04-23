package com.hsbc;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class ProbabilisticRandomGenImpl implements ProbabilisticRandomGen {
    private final Distribution distribution;
    private final Random random;

    public ProbabilisticRandomGenImpl(List<NumAndProbability> distribution) {
        this.distribution = new Distribution(distribution);
        this.random = new Random();
    }

    @Override
    public int nextFromSample() {
        float randomValue = random.nextFloat();
        for(NumAndProbabilityAndAccumulation distribute : distribution.getDistribution()){
            if(randomValue<=distribute.probabilityAccumulation()){
                return distribute.numAndProbability().number();
            }
        }
        log.warn("Return zero as default !");
        return 0;
    }
}
