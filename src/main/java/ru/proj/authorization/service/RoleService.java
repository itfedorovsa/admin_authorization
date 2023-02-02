package ru.proj.authorization.service;

import ru.proj.authorization.model.Role;

import java.util.List;
import java.util.Optional;

/**
 * Role service interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
public interface RoleService {

    List<Role> findAllRoles();

    Optional<Role> findRoleById(int roleId);

}
