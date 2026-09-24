package com.roster.persistence.jdbc.repository;

import com.roster.core.department.DepartmentStatistics;
import com.roster.persistence.jdbc.infrastructure.connection.ConnectionFactory;
import com.roster.persistence.jdbc.infrastructure.sql.SqlQueryLoader;
import com.roster.persistence.repository.department.DepartmentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class JdbcDepartmentRepository implements DepartmentRepository {
    private static final String FIND_STATISTICS_SQL_PATH = "sql/department/find_statistics.sql";
    private static final String EMPLOYEE_COUNT_COLUMN = "employee_count";
    private static final String DEPARTMENT_NAME_COLUMN = "department_name";

    private final ConnectionFactory connectionFactory;
    private final String findStatisticsSql;

    public JdbcDepartmentRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
        this.findStatisticsSql = SqlQueryLoader.load(FIND_STATISTICS_SQL_PATH);
    }

    @Override
    public List<DepartmentStatistics> findStatistics() {
        List<DepartmentStatistics> statistics = new ArrayList<>();

        try (
                Connection connection = connectionFactory.create();
                PreparedStatement statement = connection.prepareStatement(findStatisticsSql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                statistics.add(new DepartmentStatistics(
                        resultSet.getLong(EMPLOYEE_COUNT_COLUMN),
                        resultSet.getString(DEPARTMENT_NAME_COLUMN)
                ));
            }
        }
        catch (SQLException ex) {
            throw new IllegalStateException("Failed to load department statistics", ex);
        }

        return statistics;
    }
}
