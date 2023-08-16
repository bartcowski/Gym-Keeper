package com.github.bartcowski.gymkeeper.app;

import com.github.bartcowski.gymkeeper.domain.user.BodyFatPercentage;
import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserIndexesProvider {

    private final UserRepository userRepository;

    public double getUserBMI(UserId userId) {
        return userRepository.findUserById(userId)
                .map(User::calculateBMI)
                .orElse(0.0);
    }

    public double getUserFFMI(UserId userId, BodyFatPercentage bodyFatPercentage) {
        return userRepository.findUserById(userId)
                .map(user -> user.calculateFFMI(bodyFatPercentage))
                .orElse(0.0);
    }

}
