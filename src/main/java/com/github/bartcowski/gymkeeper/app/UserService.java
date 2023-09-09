package com.github.bartcowski.gymkeeper.app;

import com.github.bartcowski.gymkeeper.domain.user.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> findUserById(UserId userId) {
        return userRepository.findUserById(userId);
    }

    public Optional<User> findUserByName(Username username) {
        return userRepository.findUserByName(username);
    }

    public void addUser(CreateUserCommand command) {
        userRepository.addUser(command);
    }

    public void deleteUser(UserId userId) {
        userRepository.deleteUser(userId);
    }

    @Transactional
    public void updateUserWeight(UserId userId, UserWeight weight) {
        User user = userRepository.findUserById(userId)
                .map(u -> u.withUpdatedWeight(weight))
                .orElseThrow(() -> new IllegalStateException("User's weight cannot be updated because no user of id: " + userId.id() + " can be found"));
        userRepository.updateUser(user);
    }
}
