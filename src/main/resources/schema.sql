-- Eliminar tablas al ejecutar
DROP TABLE IF EXISTS EMPLEADOS;
DROP TABLE IF EXISTS DEPARTAMENTOS;

-- Creacion de tablas
CREATE TABLE IF NOT EXISTS DEPARTAMENTOS
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre TEXT NOT NULL UNIQUE,
    presupuesto DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS EMPLEADOS
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    departamento_id BIGINT REFERENCES DEPARTAMENTOS (id)
);

-- DATOS INICIALES

INSERT INTO DEPARTAMENTOS (nombre, presupuesto)
VALUES ('Interfaces', 500.0);
INSERT INTO DEPARTAMENTOS (nombre, presupuesto)
VALUES ('Administracion', 700.0);
INSERT INTO DEPARTAMENTOS (nombre, presupuesto)
VALUES ('PSP', 400.0);

INSERT INTO EMPLEADOS (name, email, departamento_id)
VALUES ('Mario', 'mario@gmail.com', 1);
INSERT INTO EMPLEADOS (name, email, departamento_id)
VALUES ('Alysys', 'alysys@gmail.com', 2);
INSERT INTO EMPLEADOS (name, email, departamento_id)
VALUES ('Vincent', 'vincent@gmail.com', 2);
INSERT INTO EMPLEADOS (name, email)
VALUES ('Kratos', 'kratos@gmail.com');


