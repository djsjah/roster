INSERT INTO positions (name)
VALUES
    ('Software Engineer'),
    ('QA Engineer'),
    ('Project Manager'),
    ('Marketing Manager');

INSERT INTO departments (name)
VALUES
    ('Development'),
    ('Marketing'),
    ('Sales'),
    ('Support');

INSERT INTO document_types (name)
VALUES
    ('Passport'),
    ('INN'),
    ('SNILS');


INSERT INTO employees (
    internal_id,
    surname,
    name,
    patronymic,
    email,
    birth_date,
    position_id
)
VALUES
    (
        'AXE-1',
        'Belogolovkina',
        'Maria',
        'Alekseevna',
        'belogolovkina@example.com',
        '1995-03-15',
        (SELECT id FROM positions WHERE name = 'Software Engineer')
    ),
    (
        'AXE-2',
        'Ivanov',
        'Alexander',
        'Valeryevich',
        'ivanov@example.com',
        '1992-07-21',
        (SELECT id FROM positions WHERE name = 'Software Engineer')
    ),
    (
        'AXE-3',
        'Vasilyeva',
        'Ekaterina',
        'Alexandrovna',
        'vasilyeva@example.com',
        '1989-11-02',
        (SELECT id FROM positions WHERE name = 'Project Manager')
    ),
    (
        'AXE-4',
        'Smirnov',
        'Denis',
        NULL,
        'smirnov@example.com',
        '1998-05-30',
        (SELECT id FROM positions WHERE name = 'QA Engineer')
    ),
    (
        'AXE-5',
        'Kuznetsova',
        'Anna',
        'Andreevna',
        'kuznetsova@example.com',
        '1996-09-12',
        (SELECT id FROM positions WHERE name = 'Marketing Manager')
    );


INSERT INTO employee_departments (employee_id, department_id)
VALUES
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-1'),
        (SELECT id FROM departments WHERE name = 'Development')
    ),
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-1'),
        (SELECT id FROM departments WHERE name = 'Marketing')
    ),
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-2'),
        (SELECT id FROM departments WHERE name = 'Development')
    ),
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-3'),
        (SELECT id FROM departments WHERE name = 'Development')
    ),
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-3'),
        (SELECT id FROM departments WHERE name = 'Sales')
    ),
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-4'),
        (SELECT id FROM departments WHERE name = 'Development')
    ),
    (
        (SELECT id FROM employees WHERE internal_id = 'AXE-5'),
        (SELECT id FROM departments WHERE name = 'Marketing')
    );


INSERT INTO documents (
    number,
    issuing_organization,
    issue_date,
    employee_id,
    document_type_id
)
VALUES
    (
        '4510 123456',
        'MVD',
        '2020-06-10',
        (SELECT id FROM employees WHERE internal_id = 'AXE-1'),
        (SELECT id FROM document_types WHERE name = 'Passport')
    ),
    (
        '770123456789',
        'Federal Tax Service',
        '2023-04-17',
        (SELECT id FROM employees WHERE internal_id = 'AXE-1'),
        (SELECT id FROM document_types WHERE name = 'INN')
    ),
    (
        '4511 654321',
        'MVD',
        '2024-01-25',
        (SELECT id FROM employees WHERE internal_id = 'AXE-2'),
        (SELECT id FROM document_types WHERE name = 'Passport')
    ),
    (
        '112-233-445 95',
        'Social Fund',
        '2018-08-13',
        (SELECT id FROM employees WHERE internal_id = 'AXE-3'),
        (SELECT id FROM document_types WHERE name = 'SNILS')
    ),
    (
        '4512 987654',
        'MVD',
        '2025-02-14',
        (SELECT id FROM employees WHERE internal_id = 'AXE-5'),
        (SELECT id FROM document_types WHERE name = 'Passport')
    );