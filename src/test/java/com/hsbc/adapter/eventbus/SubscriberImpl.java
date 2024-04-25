package com.hsbc.adapter.eventbus;

import com.hsbc.domain.model.event.Subscriber;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SubscriberImpl implements Subscriber {
    private final String name;
    private final List<Object> receivedObjects;

    public SubscriberImpl(String name) {
        this.name = name;
        receivedObjects = new ArrayList<>();
    }

    @Override
    public void update(Object o) {
        System.out.println(String.format("Subscriber %s has received an event %s", this.name, o.getClass()));
        receivedObjects.add(o);
    }
}
