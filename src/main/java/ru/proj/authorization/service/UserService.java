package ru.proj.authorization.service;

import ru.proj.authorization.model.User;

import java.util.Optional;

/**
 * User service interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
public interface UserService {

    Optional<User> addUser(User user);

    void updateUser(User user);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    Optional<User> findUserByLogin(String login);

    void deleteUserByLogin(String userId);

    boolean hasAdminRights(User user);

}
