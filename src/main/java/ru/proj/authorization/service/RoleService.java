package ru.proj.authorization.service;

import ru.proj.authorization.model.Role;

import java.util.List;

/**
 * Role service interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
public interface RoleService {

    List<Role> findAllRoles();

    List<Role> findRolesByIds(List<Integer> ids);

}
