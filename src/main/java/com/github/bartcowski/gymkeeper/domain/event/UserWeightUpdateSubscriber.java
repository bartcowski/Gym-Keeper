package com.github.bartcowski.gymkeeper.domain.event;

import com.github.bartcowski.gymkeeper.app.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserWeightUpdateSubscriber implements DomainEventSubscriber<WeightLogEntryAdded>{

    private final UserService userService;

    @Override
    public void handleEvent(WeightLogEntryAdded event) {
        userService.updateUserWeight(event.getUserId(), event.getWeight());
    }

    @Override
    public Class<WeightLogEntryAdded> subbedToEventType() {
        return WeightLogEntryAdded.class;
    }
}
