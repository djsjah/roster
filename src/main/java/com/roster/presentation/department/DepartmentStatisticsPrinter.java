package com.roster.presentation.department;

import com.roster.core.department.DepartmentStatistics;

import java.util.List;

public final class DepartmentStatisticsPrinter {
    private DepartmentStatisticsPrinter() { }

    public static void print(List<DepartmentStatistics> statistics) {
        String header = "%-30s %s".formatted("Department", "Employees");

        System.out.println(header);
        System.out.println("-".repeat(header.length()));

        for (DepartmentStatistics statistic : statistics) {
            System.out.printf(
                    "%-30s %d%n",
                    statistic.departmentName(),
                    statistic.employeeCount()
            );
        }
    }
}
