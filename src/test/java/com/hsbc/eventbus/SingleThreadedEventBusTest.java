package com.hsbc.eventbus;

import com.hsbc.adapter.eventbus.SingleThreadedEventBus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleThreadedEventBusTest {
    private static final SingleThreadedEventBus SINGLE_THREADED_EVENT_BUS = new SingleThreadedEventBus();


    @Test
    void publishEvent() {
        // Given
        SubscriberImpl subscriber1 = new SubscriberImpl("Subscriber 1");
        SubscriberImpl subscriber2 = new SubscriberImpl("Subscriber 2");
        SubscriberWithFilterImpl subscriberWithFilter1 = new SubscriberWithFilterImpl("Subscriber with filter 1");
        SubscriberWithFilterImpl subscriberWithFilter2 = new SubscriberWithFilterImpl("Subscriber with filter 2");
        SINGLE_THREADED_EVENT_BUS.addSubscriber(Integer.class, subscriber1);
        SINGLE_THREADED_EVENT_BUS.addSubscriber(Integer.class, subscriber2);
        SINGLE_THREADED_EVENT_BUS.addSubscriberForFilteredEvents(String.class, subscriberWithFilter1);
        SINGLE_THREADED_EVENT_BUS.addSubscriberForFilteredEvents(String.class, subscriberWithFilter2);
        SINGLE_THREADED_EVENT_BUS.addSubscriber(Integer.class, subscriberWithFilter2);
        // When
        SINGLE_THREADED_EVENT_BUS.publishEvent(new String("Event 1"));
        SINGLE_THREADED_EVENT_BUS.publishEvent(new String("Event 2"));
        SINGLE_THREADED_EVENT_BUS.publishEvent(Integer.valueOf(1));
        SINGLE_THREADED_EVENT_BUS.publishEvent(Integer.valueOf(2));
        SINGLE_THREADED_EVENT_BUS.publishEvent(Float.valueOf(2));
        // Then
        assertEquals(2, subscriber1.getReceivedObjects().size());
        assertEquals(2, subscriber2.getReceivedObjects().size());
        assertEquals(2, subscriberWithFilter1.getReceivedObjects().size());
        assertEquals(4, subscriberWithFilter2.getReceivedObjects().size());
    }
}