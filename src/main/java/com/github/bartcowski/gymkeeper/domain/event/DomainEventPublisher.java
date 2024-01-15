package com.github.bartcowski.gymkeeper.domain.event;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DomainEventPublisher {

    private final List<DomainEventSubscriber> subscribers;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public DomainEventPublisher(Optional<List<DomainEventSubscriber>> potentialSubscribers) {
        this.subscribers = potentialSubscribers.orElseGet(ArrayList::new);
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
