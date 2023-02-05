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

    Optional<User> addUser(User user);

    void updateUser(User user);

    Optional<User> findUserByLoginAndPassword(String login, String password);

    Optional<User> findUserByLogin(String userLogin);

    void deleteUserByLogin(String userLogin);

}
