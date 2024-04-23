package com.hsbc.eventbus;

public interface EventBus {
    // Feel free to replace Object with something more specific, // but be prepared to justify it
    // Zak : I prefer Object to keep publishEvent generic
    void publishEvent(Object o);

    // How would you denote the subscriber?
    // zak : using an interface, low coupling
    void addSubscriber(Class<?> eventType, Subscriber subscriber);

    // Would you allow clients to filter the events they receive? How would the interface look like?
    // zak : they can filter events by implementing SubscriberWithFilter
    void addSubscriberForFilteredEvents(Class<?> eventType, SubscriberWithFilter subscriber);
}
