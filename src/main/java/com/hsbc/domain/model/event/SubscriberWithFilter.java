package com.hsbc.domain.model.event;

public interface SubscriberWithFilter extends Subscriber {
    boolean isValid(Object event);
}
