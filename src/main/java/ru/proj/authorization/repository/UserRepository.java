package ru.proj.authorization.repository;


import ru.proj.authorization.model.User;

import java.util.Optional;

/**
 * User repository interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 06.12.22
 */
public interface UserRepository {

    Optional<User> add(User user);

    void update(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

}
