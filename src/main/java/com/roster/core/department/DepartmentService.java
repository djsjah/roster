package com.roster.core.department;

import com.roster.persistence.repository.department.DepartmentRepository;

import java.util.List;
import java.util.Objects;

public final class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = Objects.requireNonNull(departmentRepository);
    }

    public List<DepartmentStatistics> getStatistics() { return departmentRepository.findStatistics(); }
}
