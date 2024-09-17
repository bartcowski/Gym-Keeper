package com.github.bartcowski.gymkeeper.app.user;

import com.github.bartcowski.gymkeeper.domain.user.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        long id = userRepository.nextIdentity();
        User newUser = new User(
                new UserId(id),
                command.username(),
                command.gender(),
                command.age(),
                command.weight(),
                command.height());
        userRepository.addUser(newUser);
        return UserDTO.fromDomain(newUser);
    }

    @Transactional
    public void deleteUser(UserId userId) {
        userRepository.deleteUser(userId);
    }

    @Transactional
    public UserDTO updateUserData(UserId userId, UpdateUserDataCommand command) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException("User's data cannot be updated because no user of id: " + userId.id() + " can be found"));
        user.updateAge(command.age());
        user.updateWeight(command.weight());
        user.updateHeight(command.height());
        return UserDTO.fromDomain(user);
    }

    @Transactional
    public void updateUserWeight(UserId userId, UserWeight weight) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException("User's weight cannot be updated because no user of id: " + userId.id() + " can be found"));
        user.updateWeight(weight);
    }
}
