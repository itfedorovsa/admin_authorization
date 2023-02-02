package ru.proj.authorization.repository;

import ru.proj.authorization.model.Department;

import java.util.List;
import java.util.Optional;

/**
 * Department repository interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
public interface DepartmentRepository {

    List<Department> findAllDepartments();

    Optional<Department> findDepartmentById(int departmentId);

}
