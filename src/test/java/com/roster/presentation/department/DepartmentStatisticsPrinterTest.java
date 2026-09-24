package com.roster.presentation.department;

import com.roster.core.department.DepartmentStatistics;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartmentStatisticsPrinterTest {
    @Test
    void printsDepartmentStatisticsAsTable() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            DepartmentStatisticsPrinter.print(List.of(
                    new DepartmentStatistics(4, "Development"),
                    new DepartmentStatistics(2, "Marketing")
            ));
        }
        finally {
            System.setOut(originalOut);
        }

        List<String> lines = output
                .toString(StandardCharsets.UTF_8)
                .lines()
                .toList();

        assertEquals(4, lines.size());
        assertEquals("Department Employees", normalizeSpaces(lines.get(0)));
        assertTrue(lines.get(1).matches("-+"));
        assertEquals("Development 4", normalizeSpaces(lines.get(2)));
        assertEquals("Marketing 2", normalizeSpaces(lines.get(3)));
    }

    private static String normalizeSpaces(String value) {
        return value.trim().replaceAll("\\s+", " ");
    }
}
