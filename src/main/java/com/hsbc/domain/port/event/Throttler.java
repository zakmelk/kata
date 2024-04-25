package com.hsbc.domain.port.event;


import com.hsbc.domain.model.event.Statistics;

public interface Throttler {
    ThrottleResult shouldProceed(Statistics statistics);

    void notifyWhenCanProceed(Statistics statistics);

    enum ThrottleResult {
        PROCEED,
        DO_NOT_PROCEED
    }
}
