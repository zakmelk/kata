package com.hsbc.adapter.eventbus;

import com.hsbc.domain.model.event.EventBus;
import com.hsbc.domain.model.event.LatestEventSubscriber;
import com.hsbc.domain.model.event.Subscriber;
import com.hsbc.domain.model.event.SubscriberWithFilter;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class MultiThreadedEventBus implements EventBus {
    private final Map<Class<?>, Set<Subscriber>> subscribersWithoutFilter;
    private final Map<Class<?>, Set<Subscriber>> subscribersWithFilter;
    private final ConcurrentHashMap<Class<?>, Object> latestEvents;

    public MultiThreadedEventBus() {
        this.subscribersWithoutFilter = new ConcurrentHashMap<>();
        this.subscribersWithFilter = new ConcurrentHashMap<>();
        this.latestEvents = new ConcurrentHashMap<>();
    }

    @Override
    public void publishEvent(Object event) {
        if (Objects.nonNull(event)) {
            publishEventWithoutFilter(event);
            publishEventWithFilter(event);
        }
    }

    private void publishEventWithoutFilter(Object event) {
        Set<Subscriber> subscribers = subscribersWithoutFilter.getOrDefault(event.getClass(), new CopyOnWriteArraySet<>());
        updateLastEvent(event, subscribers);
        for (Subscriber subscriber : subscribers) {
            updateSubscriber(event, subscriber);
        }
    }

    private void updateSubscriber(Object event, Subscriber subscriber) {
        if (subscriber instanceof LatestEventSubscriber) {
            ((LatestEventSubscriber) subscriber).onLatestEvent(latestEvents.get(event.getClass()));
        } else {
            subscriber.update(event);
        }
    }

    private void updateLastEvent(Object o, Set<Subscriber> subscribers) {
        if (subscribers.stream().anyMatch(s -> s instanceof LatestEventSubscriber)) {
            latestEvents.put(o.getClass(), o);
        }
    }

    private void publishEventWithFilter(Object event) {
        Set<Subscriber> subscribers = subscribersWithFilter.getOrDefault(event.getClass(), new CopyOnWriteArraySet<>());
        updateLastEvent(event, subscribers);
        for (Subscriber subscriber : subscribers) {
            if (((SubscriberWithFilter) subscriber).isValid(event)) {
                updateSubscriber(event, subscriber);
            }
        }
    }

    @Override
    public void addSubscriber(Class<?> eventType, Subscriber subscriber) {
        subscribersWithoutFilter.computeIfAbsent(eventType, key -> new HashSet<>()).add(subscriber);
    }

    @Override
    public void addSubscriberForFilteredEvents(Class<?> eventType, SubscriberWithFilter subscriber) {
        subscribersWithFilter.computeIfAbsent(eventType, key -> new HashSet<>()).add(subscriber);
    }
}
