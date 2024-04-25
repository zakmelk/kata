package com.hsbc.adapter.eventbus;

import com.hsbc.domain.model.event.SubscriberWithFilter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SubscriberWithFilterImpl implements SubscriberWithFilter {
    private final String name;
    private final List<Object> receivedObjects;

    public SubscriberWithFilterImpl(String name) {
        this.name = name;
        receivedObjects = new ArrayList<>();
    }

    @Override
    public void update(Object o) {
        System.out.println(String.format("Subscriber %s has received an event %s", name, o.getClass()));
        receivedObjects.add(o);
    }

    @Override
    public boolean isValid(Object event) {
        return event instanceof String;
    }
}
