package com.github.bartcowski.gymkeeper.domain.event;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.UserWeight;

public record WeightLogEntryAdded(
        UserId userId,
        UserWeight weight) implements DomainEvent {

}
