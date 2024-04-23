package com.hsbc.eventbus;

public interface SubscriberWithFilter extends Subscriber{
    boolean isValid(Object event);
}
