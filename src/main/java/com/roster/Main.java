package com.roster;

import com.roster.core.department.DepartmentService;
import com.roster.core.department.DepartmentStatistics;
import com.roster.presentation.department.DepartmentStatisticsPrinter;
import com.roster.persistence.jdbc.infrastructure.config.DatabaseConfig;
import com.roster.persistence.jdbc.infrastructure.config.DatabaseConfigLoader;
import com.roster.persistence.jdbc.infrastructure.connection.ConnectionFactory;
import com.roster.persistence.repository.department.DepartmentRepository;
import com.roster.persistence.jdbc.repository.JdbcDepartmentRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application started");

        try {
            DatabaseConfig databaseConfig = DatabaseConfigLoader.load();
            ConnectionFactory connectionFactory = new ConnectionFactory(databaseConfig);

            DepartmentRepository departmentRepository = new JdbcDepartmentRepository(connectionFactory);
            DepartmentService departmentService = new DepartmentService(departmentRepository);

            List<DepartmentStatistics> statistics = departmentService.getStatistics();
            LOGGER.info("Department statistics loaded successfully. Rows: " + statistics.size());

            DepartmentStatisticsPrinter.print(statistics);
            LOGGER.info("Application finished successfully");
        }
        catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Application failed", ex);
            System.exit(1);
        }
    }
}