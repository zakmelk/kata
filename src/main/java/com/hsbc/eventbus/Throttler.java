package com.hsbc.eventbus;


public interface Throttler {
    ThrottleResult shouldProceed(Statistics statistics);

    void notifyWhenCanProceed(Statistics statistics);

    enum ThrottleResult {
        PROCEED,
        DO_NOT_PROCEED
    }
}
