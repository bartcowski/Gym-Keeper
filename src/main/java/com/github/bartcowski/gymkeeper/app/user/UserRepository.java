package com.github.bartcowski.gymkeeper.app.user;

import com.github.bartcowski.gymkeeper.domain.user.CreateUserCommand;
import com.github.bartcowski.gymkeeper.domain.user.User;
import com.github.bartcowski.gymkeeper.domain.user.UserId;
import com.github.bartcowski.gymkeeper.domain.user.Username;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAllUsers();

    Optional<User> findUserById(UserId userId);

    Optional<User> findUserByName(Username username);

    User addUser(CreateUserCommand command);

    void deleteUser(UserId userId);

}
