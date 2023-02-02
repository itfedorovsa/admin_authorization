package ru.proj.authorization.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.proj.authorization.model.Role;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    private static final String FIND_ALL_ROLES = "FROM Role";

    private static final String FIND_ROLE_BY_ID = "FROM Role WHERE id = :rId";

    private final CrudRepository crudRepository;

    /**
     * Find all Role
     *
     * @return List of Role
     */
    @Override
    public List<Role> findAllRoles() {
        return crudRepository.query(FIND_ALL_ROLES, Role.class);
    }

    /**
     * Find Role by id
     *
     * @param roleId Role id
     * @return Optional of Role or empty Optional
     */
    @Override
    public Optional<Role> findRoleById(int roleId) {
        return crudRepository.optional(
                FIND_ROLE_BY_ID,
                Role.class,
                Map.of("rId", roleId)
        );
    }

}
