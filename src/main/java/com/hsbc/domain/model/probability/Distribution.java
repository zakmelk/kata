package com.hsbc.domain.model.probability;

import com.hsbc.domain.port.probability.ProbabilisticRandomGen;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Distribution implements ProbabilisticRandomGen {
    private final List<NumAndProbabilityAndAccumulation> distribution;
    private final float distributionProbabilitySum;

    public Distribution(List<NumAndProbability> pDistribution) {
        if (Objects.nonNull(pDistribution) && !pDistribution.isEmpty()) {
            distribution = new ArrayList<>();
            distribution.add(new NumAndProbabilityAndAccumulation(pDistribution.get(0), pDistribution.get(0).probabilityOfSample()));
            for (int i = 1; i < pDistribution.size(); i++) {
                distribution.add(new NumAndProbabilityAndAccumulation(pDistribution.get(i), pDistribution.get(i).probabilityOfSample() + distribution.get(i - 1).probabilityAccumulation()));
            }
            distributionProbabilitySum = distribution.get(distribution.size() - 1).probabilityAccumulation();
        } else {
            throw new IllegalArgumentException("Distribution list should not be null or empty");
        }

    }

    @Override
    public int nextFromSample() {
        throw new IllegalCallerException();
    }
}
