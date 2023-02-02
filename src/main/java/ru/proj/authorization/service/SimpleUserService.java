package ru.proj.authorization.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.proj.authorization.model.User;
import ru.proj.authorization.repository.UserRepository;

import java.util.Optional;

/**
 * User service layer
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleUserService implements UserService {

    private final UserRepository store;

    @Override
    public Optional<User> addUser(User user) {
        return store.add(user);
    }

    @Override
    public void updateUser(User user) {
        store.update(user);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return store.findByLoginAndPassword(login, password);
    }

}
