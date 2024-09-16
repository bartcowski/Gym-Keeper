package com.github.bartcowski.gymkeeper.app.user;

import com.github.bartcowski.gymkeeper.domain.user.BodyFatPercentage;
import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserIndexesProvider {

    private final UserRepository userRepository;

    public UserIndexesProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
