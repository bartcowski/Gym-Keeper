package com.github.bartcowski.gymkeeper.app.user;

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

    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        return userRepository.findAllUsers()
                .stream()
                .map(UserDTO::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserById(UserId userId) {
        return userRepository.findUserById(userId).map(UserDTO::fromDomain);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByName(Username username) {
        return userRepository.findUserByName(username).map(UserDTO::fromDomain);
    }

    @Transactional
    public UserDTO addUser(CreateUserCommand command) {
        User user = userRepository.addUser(command);
        return UserDTO.fromDomain(user);
    }

    @Transactional
    public void deleteUser(UserId userId) {
        userRepository.deleteUser(userId);
    }

    @Transactional
    public void updateUserWeight(UserId userId, UserWeight weight) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException("User's weight cannot be updated because no user of id: " + userId.id() + " can be found"));
        user.updateWeight(weight);
    }
}
