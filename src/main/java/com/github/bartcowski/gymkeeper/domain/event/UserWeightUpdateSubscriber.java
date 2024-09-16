package com.github.bartcowski.gymkeeper.domain.event;

import com.github.bartcowski.gymkeeper.app.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserWeightUpdateSubscriber implements DomainEventSubscriber<WeightLogEntryAdded> {

    private final UserService userService;

    public UserWeightUpdateSubscriber(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleEvent(WeightLogEntryAdded event) {
        userService.updateUserWeight(event.userId(), event.weight());
    }

    @Override
    public Class<WeightLogEntryAdded> subbedToEventType() {
        return WeightLogEntryAdded.class;
    }
}
