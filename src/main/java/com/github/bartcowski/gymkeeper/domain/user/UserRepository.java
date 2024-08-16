package com.github.bartcowski.gymkeeper.domain.user;

import com.github.bartcowski.gymkeeper.domain.IdGeneratingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends IdGeneratingRepository {

    List<User> findAllUsers();

    Optional<User> findUserById(UserId userId);

    Optional<User> findUserByName(Username username);

    User addUser(CreateUserCommand command);

    void deleteUser(UserId userId);

}
