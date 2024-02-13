package com.github.bartcowski.gymkeeper.domain.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DomainEventPublisher {

    private final List<DomainEventSubscriber> subscribers;

    private final ExecutorService executorService;

    public DomainEventPublisher(
            Optional<List<DomainEventSubscriber>> potentialSubscribers,
            @Value("${gymkeeper.event.publisher.threadPoolSize:5}") int threadPoolSize) {
        this.subscribers = potentialSubscribers.orElseGet(ArrayList::new);
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public <T extends DomainEvent> void publish(T domainEvent) {
        Class<?> eventType = domainEvent.getClass();
        subscribers.stream()
                .filter(sub -> sub.subbedToEventType().equals(eventType))
                .forEach(sub -> sub.handleEvent(domainEvent));
    }

    public <T extends DomainEvent> void publishAsync(T domainEvent) {
        Class<?> eventType = domainEvent.getClass();
        subscribers.stream()
                .filter(sub -> sub.subbedToEventType().equals(eventType))
                .forEach(sub -> executorService.submit(() -> sub.handleEvent(domainEvent)));
    }
}
