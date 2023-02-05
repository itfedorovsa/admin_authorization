package ru.proj.authorization.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.proj.authorization.model.Role;
import ru.proj.authorization.model.User;
import ru.proj.authorization.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
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
        return store.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        store.updateUser(user);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return store.findUserByLoginAndPassword(login, password);
    }

    @Override
    public Optional<User> findUserByLogin(String userLogin) {
        return store.findUserByLogin(userLogin);
    }

    @Override
    public void deleteUserByLogin(String userLogin) {
        store.deleteUserByLogin(userLogin);
    }

    /**
     * Check User for administrator rights
     *
     * @param user User
     * @return true if User has admin rights otherwise false
     */
    @Override
    public boolean hasAdminRights(User user) {
        boolean isAdmin = false;
        List<Role> roles = user.getRoles();
        if (roles == null) {
            user.setRoles(new ArrayList<>());
            return false;
        }
        for (Role role : roles) {
            if ("Administrator".equals(role.getName())) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

}
