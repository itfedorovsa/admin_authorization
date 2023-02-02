package ru.proj.authorization.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.proj.authorization.model.Role;
import ru.proj.authorization.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Role service layer
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleRoleService implements RoleService {

    private final RoleRepository store;

    @Override
    public List<Role> findAllRoles() {
        return store.findAllRoles();
    }

    @Override
    public Optional<Role> findRoleById(int roleId) {
        return store.findRoleById(roleId);
    }

}
