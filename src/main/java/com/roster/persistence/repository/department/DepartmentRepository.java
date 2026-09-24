package com.roster.persistence.repository.department;

import com.roster.core.department.DepartmentStatistics;

import java.util.List;

public interface DepartmentRepository {
    List<DepartmentStatistics> findStatistics();
}
