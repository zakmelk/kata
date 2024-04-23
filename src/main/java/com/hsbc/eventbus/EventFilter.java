package com.hsbc.eventbus;

public interface EventFilter {
    boolean isValid(Object event);
}
