package com.hsbc.eventbus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class MultiThreadedEventBus implements EventBus{
    private final Map<Class<?>, Set<Subscriber>> subscribersWithoutFilter = new ConcurrentHashMap<>();
    private final Map<Class<?>, Set<SubscriberWithFilter>> subscribersWithFilter = new ConcurrentHashMap<>();

    @Override
    public void publishEvent(Object event) {
        if(Objects.nonNull(event)){
            publishEventWithoutFilter(event);
            publishEventWithFilter(event);
        }
    }

    private void publishEventWithoutFilter(Object o) {
        Set<Subscriber> subscribers = subscribersWithoutFilter.getOrDefault(o.getClass(), new CopyOnWriteArraySet<>());
        for(Subscriber subscriber : subscribers){
            subscriber.update(o);
        }
    }

    private void publishEventWithFilter(Object event) {
        Set<SubscriberWithFilter> subscribers = subscribersWithFilter.getOrDefault(event.getClass(), new CopyOnWriteArraySet<>());
        for(SubscriberWithFilter subscriber : subscribers){
            if(subscriber.isValid(event)){
                subscriber.update(event);
            }
        }
    }

    @Override
    public void addSubscriber(Class<?> eventType, Subscriber subscriber) {
        subscribersWithoutFilter.computeIfAbsent(eventType, key->new HashSet<>()).add(subscriber);
    }

    @Override
    public void addSubscriberForFilteredEvents(Class<?> eventType, SubscriberWithFilter subscriber) {
        subscribersWithFilter.computeIfAbsent(eventType,key->new HashSet<>()).add(subscriber);
    }
}
