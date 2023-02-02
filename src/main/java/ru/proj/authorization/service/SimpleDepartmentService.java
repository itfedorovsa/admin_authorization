package ru.proj.authorization.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.proj.authorization.model.Department;
import ru.proj.authorization.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Department service layer
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 02.02.23
 */
@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleDepartmentService implements DepartmentService {

    private final DepartmentRepository store;

    @Override
    public List<Department> findAllDepartments() {
        return store.findAllDepartments();
    }

    @Override
    public Optional<Department> findDepartmentById(int departmentId) {
        return store.findDepartmentById(departmentId);
    }

}
