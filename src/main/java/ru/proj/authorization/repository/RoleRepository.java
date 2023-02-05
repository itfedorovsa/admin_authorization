package ru.proj.authorization.repository;

import ru.proj.authorization.model.Role;

import java.util.List;

/**
 * Role repository interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
public interface RoleRepository {

    List<Role> findAllRoles();

    List<Role> findRolesByIds(List<Integer> ids);

}
