package com.hsbc.probabilisticrandom;

import com.hsbc.probabilisticrandom.Distribution;
import com.hsbc.probabilisticrandom.ProbabilisticRandomGen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistributionTest {

    private static Distribution distribution;

    @BeforeAll
    public static void setup(){
        ProbabilisticRandomGen.NumAndProbability numAndProbability1 = new ProbabilisticRandomGen.NumAndProbability(1,0.2f);
        ProbabilisticRandomGen.NumAndProbability numAndProbability2 = new ProbabilisticRandomGen.NumAndProbability(2,0.3f);
        ProbabilisticRandomGen.NumAndProbability numAndProbability3 = new ProbabilisticRandomGen.NumAndProbability(3,0.1f);
        ProbabilisticRandomGen.NumAndProbability numAndProbability4 = new ProbabilisticRandomGen.NumAndProbability(4,0.2f);
        List<ProbabilisticRandomGen.NumAndProbability> pDistribution = List.of(numAndProbability1,numAndProbability2,numAndProbability3,numAndProbability4);
        distribution = new Distribution(pDistribution);
    }

    @Test
    void nextFromSample() {
        assertThrows(IllegalCallerException.class,()->distribution.nextFromSample());
    }

    @Test
    void getDistribution() {
        assertNotNull(distribution.getDistribution());
    }

    @Test
    void getDistributionProbabilitySum() {
        assertEquals(0.8f,distribution.getDistributionProbabilitySum());
    }
}