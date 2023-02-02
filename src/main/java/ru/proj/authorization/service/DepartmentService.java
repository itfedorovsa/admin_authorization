package ru.proj.authorization.service;

import ru.proj.authorization.model.Department;

import java.util.List;
import java.util.Optional;

/**
 * Department service interface
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
public interface DepartmentService {

    List<Department> findAllDepartments();

    Optional<Department> findDepartmentById(int departmentId);

}
