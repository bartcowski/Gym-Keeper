package com.github.bartcowski.gymkeeper.domain.event;

public interface DomainEventSubscriber<T extends DomainEvent> {

    void handleEvent(T event);

    Class<T> subbedToEventType();

}
