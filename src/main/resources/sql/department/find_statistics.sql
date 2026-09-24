SELECT
    d.name AS department_name,
    COUNT(ed.employee_id) AS employee_count
FROM departments d
LEFT OUTER JOIN employee_departments ed ON d.id = ed.department_id
GROUP BY d.id
ORDER BY d.name;