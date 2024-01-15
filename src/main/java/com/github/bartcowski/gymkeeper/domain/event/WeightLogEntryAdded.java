package com.github.bartcowski.gymkeeper.domain.event;

import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.UserWeight;
import lombok.Value;

@Value
public class WeightLogEntryAdded implements DomainEvent {

    UserId userId;

    UserWeight weight;

}
