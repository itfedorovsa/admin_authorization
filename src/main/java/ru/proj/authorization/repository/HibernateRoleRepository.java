package ru.proj.authorization.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.proj.authorization.model.Role;

import java.util.List;
import java.util.Map;

/**
 * Hibernate Role repository
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Repository
@AllArgsConstructor
@ThreadSafe
public class HibernateRoleRepository implements RoleRepository {

    private static final String FIND_ALL_ROLES_ORDER_BY_NAME_ASC = "FROM Role ORDER BY name ASC";

    private static final String FIND_ROLES_BY_ID = "FROM Role WHERE id IN (:rIds)";

    private final CrudRepository crudRepository;

    /**
     * Find all Role
     *
     * @return List of Role
     */
    @Override
    public List<Role> findAllRoles() {
        return crudRepository.query(FIND_ALL_ROLES_ORDER_BY_NAME_ASC, Role.class);
    }

    /**
     * Find list of Role by id/ids
     *
     * @param ids list of Role id/ids
     * @return list of Role
     */
    @Override
    public List<Role> findRolesByIds(List<Integer> ids) {
        return crudRepository.query(
                FIND_ROLES_BY_ID,
                Role.class,
                Map.of("rIds", ids));
    }

}
