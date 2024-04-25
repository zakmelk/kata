package com.hsbc.adapter.eventbus;

import com.hsbc.domain.model.event.LatestEventSubscriber;
import com.hsbc.domain.model.event.Subscriber;
import com.hsbc.domain.model.event.SubscriberWithFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MultiThreadedEventBusTest {

    private static MultiThreadedEventBus eventBus;

    @BeforeEach
    void setUp() {
        eventBus = new MultiThreadedEventBus();
    }

    @Test
    void testAddSubscriber() {
        // Given
        Subscriber subscriber = mock(Subscriber.class);
        // when
        eventBus.addSubscriber(TestEvent.class, subscriber);
        // Then
        assertEquals(1, eventBus.getSubscribersWithoutFilter().size());
        assertEquals(0, eventBus.getSubscribersWithFilter().size());
    }

    @Test
    void testAddSubscriberForFilteredEvents() {
        // Given
        SubscriberWithFilter subscriber = mock(SubscriberWithFilter.class);
        // When
        eventBus.addSubscriberForFilteredEvents(TestEvent.class, subscriber);
        // Then
        assertEquals(0, eventBus.getSubscribersWithoutFilter().size());
        assertEquals(1, eventBus.getSubscribersWithFilter().size());
    }

    @Test
    void testPublishEventWithoutSubscriber() {
        assertDoesNotThrow(() -> eventBus.publishEvent(new TestEvent()));
    }

    @Test
    void testPublishForFilteredEvents() {
        // Given
        SubscriberWithFilter subscriber = mock(SubscriberWithFilter.class);
        when(subscriber.isValid(any())).thenReturn(true);
        // When
        eventBus.addSubscriberForFilteredEvents(TestEvent.class, subscriber);
        eventBus.publishEvent(new TestEvent());
        // Then
        verify(subscriber).update(any());
    }

    @Test
    void testPublishEventWithInvalidFilter() {
        // Given
        SubscriberWithFilter subscriber = mock(SubscriberWithFilter.class);
        when(subscriber.isValid(any())).thenReturn(false);
        // When
        eventBus.addSubscriberForFilteredEvents(TestEvent.class, subscriber);
        eventBus.publishEvent(new TestEvent());
        // Then
        verify(subscriber, never()).update(any());
    }

    @Test
    void testLatestEventSubscriberReceivesLatestEvent() throws InterruptedException {
        // Given
        LatestEventSubscriber subscriber = mock(LatestEventSubscriber.class);
        // When
        eventBus.addSubscriber(TestEvent.class, subscriber);
        eventBus.publishEvent(new TestEvent());
        // Then
        verify(subscriber).onLatestEvent(any());
    }

    @Test
    void testConcurrentAccessToSubscribersWithoutFilter() throws InterruptedException {
        // Given
        final int threadCount = 10;
        final int iterationsPerThread = 100;
        MultiThreadedEventBus eventBus = new MultiThreadedEventBus();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    for (int j = 0; j < iterationsPerThread; j++) {
                        eventBus.addSubscriber(TestEvent.class, new SubscriberImpl(String.valueOf(finalI + j)));
                        eventBus.publishEvent(new TestEvent());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // Then
        assertEquals(threadCount * iterationsPerThread, eventBus.getSubscribersWithoutFilter().get(TestEvent.class).size());
    }

    static class TestEvent {
    }
}