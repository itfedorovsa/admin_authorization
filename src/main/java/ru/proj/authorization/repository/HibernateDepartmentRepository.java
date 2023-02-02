package ru.proj.authorization.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.proj.authorization.model.Department;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Hibernate Department repository
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Repository
@AllArgsConstructor
@ThreadSafe
public class HibernateDepartmentRepository implements DepartmentRepository {

    private static final String FIND_ALL_DEPARTMENTS = "FROM Department";

    private static final String FIND_DEPARTMENT_BY_ID = "FROM Department WHERE id = :dId";

    private final CrudRepository crudRepository;

    /**
     * Find all Department
     *
     * @return List of Department
     */
    @Override
    public List<Department> findAllDepartments() {
        return crudRepository.query(FIND_ALL_DEPARTMENTS, Department.class);
    }

    /**
     * Find Department by id
     *
     * @param departmentId Department id
     * @return Optional of Department or empty Optional
     */
    @Override
    public Optional<Department> findDepartmentById(int departmentId) {
        return crudRepository.optional(
                FIND_DEPARTMENT_BY_ID,
                Department.class,
                Map.of("dId", departmentId)
        );
    }

}
