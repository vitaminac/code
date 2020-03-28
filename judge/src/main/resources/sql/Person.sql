-- ejercicio 1

-- a) Crear el dominio Tipo_Jubilado. Puede tomar los valores ‘SI’, ‘NO’ y por defecto tomará el valor ‘NO’.
-- b) Crear la tabla PERSONA, siendo el atributo DNI la clave primaria,
-- el atributo nombre una clave alternativa
-- y el atributo jubilado tomará los valores deldominio Tipo_Jubilado previamente creado.
CREATE TABLE persona (
  dni       CHAR(8) PRIMARY KEY,
  nombre    VARCHAR(63) UNIQUE,
  ciudad    VARCHAR(63)          NOT NULL,
  salario   NUMERIC              NOT NULL,
  profesion VARCHAR(63)          NOT NULL,
  fecha_nac DATE                 NOT NULL,
  jubilado  CHAR(2) DEFAULT 'NO' NOT NULL,
  CONSTRAINT jubilado_binario CHECK jubilado IN ('SI', 'NO')
);

-- c) Insertar las siguientes tuplas en la tabla
INSERT INTO persona
VALUES (11111111, 'Juan', 'Madrid', 1500, 'Informatico', PARSEDATETIME('01/05/1972', 'DD/MM/YYYY'), 'NO');
INSERT INTO persona
VALUES (22222222, 'Maria', 'Barcelona', 1800, 'Informatico', PARSEDATETIME('05/06/1980', 'DD/MM/YYYY'), 'NO');
INSERT INTO persona
VALUES (33333333, 'Pedro', 'Valencia', 1200, 'administrativo', PARSEDATETIME('31/05/1940', 'DD/MM/YYYY'), 'SI');
INSERT INTO persona
VALUES (44444444, 'Isabel', 'Madrid', 1600, 'Informatico', PARSEDATETIME('22/03/1974', 'DD/MM/YYYY'), 'NO');
INSERT INTO persona
VALUES (55555555, 'Antonio', 'Barcelona', 1000, 'administrativo', PARSEDATETIME('15/08/1935', 'DD/MM/YYYY'), 'SI');
SELECT *
FROM persona;

SELECT *
FROM persona;
-- Realizar las siguientes operaciones de manipulación
-- d) Incrementar en un 5% el salario de los informáticos.
UPDATE persona
SET salario = salario * 1.05
WHERE profesion = 'Informatico';
SELECT *
FROM persona;

SELECT *
FROM persona;
-- e) Decrementar en un 2% el salario de los que viven en Madrid.
UPDATE persona
SET salario = salario * 0.98
WHERE ciudad = 'Madrid';
SELECT *
FROM persona;

SELECT *
FROM persona;
-- f) Modificar el nombre de la persona con DNI=‘55555555’ por ‘Juan Antonio’.
UPDATE persona
SET nombre = 'Juan Antonio'
WHERE dni = '55555555';
SELECT *
FROM persona;

SELECT *
FROM persona;
-- g) Todos los jubilados pasan a ganar 100 Euros más.
UPDATE persona
SET salario = salario + 100
WHERE jubilado = 'SI';
SELECT *
FROM persona;

SELECT *
FROM persona;
-- h) Borrar todas las personas de Madrid que hayan nacido antes del 1940
DELETE
FROM persona
WHERE ciudad = 'Madrid'
  AND fecha_nac < PARSEDATETIME('01/01/1940', 'DD/MM/YYYY');
SELECT *
FROM persona;

SELECT *
FROM persona;
-- Realizar las siguientes consultas:
-- i) Obtener todas las personas ordenadas descendentemente por nombre.
SELECT *
FROM persona
ORDER BY nombre DESC;

SELECT *
FROM persona;
-- j) Obtener el sueldo medio de las personas de Madrid agrupadas por profesiones
SELECT profesion,
       avg(salario)
FROM persona
GROUP BY profesion;

SELECT *
FROM persona;
-- k) Obtener el sueldo medio de las personas de Madrid agrupadas por profesiones
-- siempre que el sueldo medio por profesión supere los 1000 euros y ordenado por profesión.
SELECT profesion,
       avg(salario)
FROM persona
WHERE ciudad = 'Madrid'
GROUP BY profesion
ORDER BY profesion;

SELECT *
FROM persona;
-- ejercicio 2
CREATE TABLE departamento (
  numero_d    INT,
  nombre_d    VARCHAR(25),
  nss_gerente VARCHAR(10),
  CONSTRAINT pk_dept PRIMARY KEY (numero_d)
);

CREATE TABLE proyecto (
  cod_p    VARCHAR(9),
  nombre_p VARCHAR(60),
  lugar_p  VARCHAR(30),
  num_dep  TINYINT,
  CONSTRAINT pk_proyecto PRIMARY KEY (cod_p),
  CONSTRAINT fk_proy_departamento
    FOREIGN KEY (num_dep) REFERENCES departamento (numero_d)
);

CREATE TABLE empleado (
  nss            VARCHAR(10),
  nombre         VARCHAR(25),
  apellidos      VARCHAR(25),
  nss_supervisor VARCHAR(10),
  fecha_nac      DATE,
  direccion      VARCHAR(30),
  ciudad         VARCHAR(15),
  sexo           VARCHAR(1),
  num_dep        TINYINT,
  salario        INT,
  CONSTRAINT pk_empleados PRIMARY KEY (nss),
  CONSTRAINT fk_emp_supervisor
    FOREIGN KEY (nss_supervisor) REFERENCES empleado (nss),
  CONSTRAINT fk_emp_deparpatamento
    FOREIGN KEY (num_dep) REFERENCES departamento (numero_d)
);

ALTER TABLE departamento
  ADD CONSTRAINT fk_dept_empleado
    FOREIGN KEY (nss_gerente) REFERENCES empleado (nss);

CREATE TABLE familiar (
  nsse       VARCHAR(10),
  nombre     VARCHAR(30),
  sexo       VARCHAR(1),
  parentesco VARCHAR(10),
  CONSTRAINT pk_familiar PRIMARY KEY (nsse, nombre),
  CONSTRAINT fk_familiar_empleado
    FOREIGN KEY (nsse) REFERENCES empleado (nss)
);

CREATE TABLE trabaja_en (
  nss_e    VARCHAR(10),
  cod_proy VARCHAR(9),
  horas    INT,
  CONSTRAINT pk_trabaja_en PRIMARY KEY (nss_e, cod_proy),
  CONSTRAINT fk_trabaja_en_emepleado
    FOREIGN KEY (nss_e) REFERENCES empleado (nss)
);

-- INSERCIÓN DE DATOS

-- TABLA EMPLEADOS sin el departamento
-- He incluido un campo ciudad para las consultas 7 y 8
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/123456',
        'JUAN', 'JIMENEZ JUAREZ', NULL, PARSEDATETIME('12/12/1945', 'DD/MM/YYYY'), 'RUE del Percebe', 'Madrid', 'H', 2800);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/342139',
        'MARÍA', 'Silvano', '28/123456', PARSEDATETIME('12/12/1975', 'DD/MM/YYYY'), 'Calle del Pez', 'Móstoles', 'M', 850);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/999999',
        'RAÚL', 'RUEDA ROMERO', '28/123456', PARSEDATETIME('23/07/1968', 'DD/MM/YYYY'), 'Calle Echegaray', 'Móstoles', 'H', 1500);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/000001',
        'VERÓNICA', 'VIERA VÁZQUEZ', '28/342139', PARSEDATETIME('18/03/1980', 'DD/MM/YYYY'), 'Plaza de Espańa', 'Moriles', 'M', 700);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/000010',
        'LUIS', 'VIERA VIERA', '28/000001', PARSEDATETIME('19/07/1970', 'DD/MM/YYYY'), 'Plaza del Rey', 'Madrid', 'H', 3050);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/000020',
        'ANTONIO', 'VÁZQUEZ VIERA', '28/000010', PARSEDATETIME('12/06/1967', 'DD/MM/YYYY'), 'Calle Europa', 'Barcelona', 'H', 3100);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/000030',
        'JESÚS', 'VIERA LÓPEZ', '28/000010', PARSEDATETIME('11/12/1956', 'DD/MM/YYYY'), 'Avenida Espańa', 'Valencia', 'H', 2500);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/000040',
        'MARIA', 'VIERA JIMÉNEZ', '28/000010', PARSEDATETIME('28/10/1981', 'DD/MM/YYYY'), 'Plaza de la Constitución', 'Sevilla', 'M', 3100);
INSERT INTO empleado (nss, nombre, apellidos, nss_supervisor, fecha_nac,
                      direccion, ciudad, sexo, salario)
VALUES ('28/000050',
        'ANA', 'VIERA GARCÍA', '28/000010', PARSEDATETIME('23/04/1989', 'DD/MM/YYYY'), 'Calle Mayor', 'Bilbao', 'M', 3100);

SELECT *
FROM empleado;

-- TABLA DEPARTAMENTO
INSERT INTO departamento (numero_d, nombre_d, nss_gerente)
VALUES (5, 'Investigación', '28/123456');

INSERT INTO departamento (numero_d, nombre_d, nss_gerente)
VALUES (2, 'Análisis', '28/123456');

INSERT INTO departamento (numero_d, nombre_d, nss_gerente)
VALUES (1, 'Dirección', '28/342139');

SELECT *
FROM departamento;

SELECT *
FROM departamento;

-- Actualizo los departamentos a los que pertenecen los empleados
UPDATE empleado
SET num_dep = 1
WHERE nss = '28/123456';

UPDATE empleado
SET num_dep = 5
WHERE nss = '28/342139';

UPDATE empleado
SET num_dep = 5
WHERE nss = '28/999999';

UPDATE empleado
SET num_dep = 2
WHERE nss = '28/000001';

UPDATE empleado
SET num_dep = 1
WHERE nss = '28/000010';

UPDATE empleado
SET num_dep = 1
WHERE nss = '28/000020';

UPDATE empleado
SET num_dep = 1
WHERE nss = '28/000030';

UPDATE empleado
SET num_dep = 1
WHERE nss = '28/000040';

UPDATE empleado
SET num_dep = 1
WHERE nss = '28/000050';

SELECT *
FROM empleado;

-- TABLA PROYECTO
INSERT INTO proyecto (cod_p, nombre_p, lugar_p, num_dep)
VALUES ('123456789', 'WEB
Ayuntamiento', 'MADRID', 5);

INSERT INTO proyecto (cod_p, nombre_p, lugar_p, num_dep)
VALUES ('123000000', 'WEB
Ministerio', 'MADRID', 5);

INSERT INTO proyecto (cod_p, nombre_p, lugar_p, num_dep)
VALUES ('999999999', 'ERP
VIPS', 'MADRID', 2);

INSERT INTO proyecto (cod_p, nombre_p, lugar_p, num_dep)
VALUES ('777777777',
        'PRESUPUESTO HOTEL SANS', 'BARCELONA', 1);

SELECT *
FROM proyecto;

-- TABLA FAMILIAR
INSERT INTO familiar (nsse, nombre, sexo, parentesco)
VALUES ('28/123456', 'JUAN', 'H', 'HIJO');

INSERT INTO familiar (nsse, nombre, sexo, parentesco)
VALUES ('28/123456', 'JUANITA', 'M', 'HIJA');

INSERT INTO familiar (nsse, nombre, sexo, parentesco)
VALUES ('28/123456', 'JUANA', 'M', 'MUJER');

SELECT *
FROM familiar;

-- TABLA TRABAJA_EN
INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/123456', '123456789', 100);

INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/342139', '123456789', 100);

INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/342139', '123000000', 100);

INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/342139', '999999999', 150);

INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/999999', '123456789', 300);

INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/999999', '999999999', 350);

INSERT INTO trabaja_en (nss_e, cod_proy, horas)
VALUES ('28/000001', '777777777', 200);

SELECT *
FROM trabaja_en;

-- 1. Obtener todas las combinaciones de empleados y departamentos.
SELECT *
FROM empleado
       CROSS JOIN departamento;

-- 2. Obtener todas las combinaciones de NSS de empleados y nombres de departamento.
SELECT nss,
       nombre_d
FROM empleado
       CROSS JOIN departamento;

-- 3. Seleccionar el salario de cada uno de los empleados
SELECT salario
FROM empleado;

-- 4. Seleccionar los distintos salarios que cobran los empleados.
SELECT DISTINCT salario
FROM empleado;

-- 5. Obtener todos los empleados del departamento 5.
SELECT *
FROM empleado
WHERE num_dep = 5;

-- 6. Seleccionar los empleados del departamento de ‘Investigación’
-- y la información de este departamento.
SELECT *
FROM empleado
       INNER JOIN departamento
                  ON empleado.num_dep = departamento.numero_d;

-- 7. Nombre y apellidos de los empleados de Móstoles
SELECT nombre,
       apellidos
FROM empleado
WHERE ciudad = 'Móstoles';

-- 8. Nombre y apellidos de los empleados que vivan en una ciudad que empiece por ‘M’ y termine por ‘les’
SELECT nombre,
       apellidos
FROM empleado
WHERE ciudad LIKE 'M%les';

-- 9. Nombre del departamento, nombre y apellidos de los empleados
-- ordenados descendentemente por nombre de departamento y ascendentemente por apellidos
SELECT departamento.nombre_d,
       empleado.nombre,
       empleado.apellidos
FROM empleado
       INNER JOIN departamento
                  ON empleado.num_dep = departamento.numero_d
ORDER BY departamento.nombre_d DESC, empleado.apellidos ASC;

-- 10. NSS de los empleados del departamento 1, 2 o 3
SELECT nss
FROM empleado
WHERE num_dep IN (1, 2, 3);

-- 11. Nombre y  apellidos de los empleados que tengan un salario desconocido
SELECT *
FROM empleado
WHERE salario IS NULL;

-- 12. Nombre y  apellidos de los empleados junto con el nombre y apellidos de su supervisor.
SELECT empleado.nombre,
       empleado.apellidos,
       supervisor.nombre    supervisor_nombre,
       supervisor.apellidos supervisor_apellidos
FROM empleado
       JOIN empleado supervisor
            ON empleado.nss_supervisor = supervisor.nss;

-- 13. Obtener el total de los salarios, el salario máximo, el salario mínimo
-- y la media del salario de todos los empleados
SELECT sum(salario),
       max(salario),
       min(salario),
       avg(salario)
FROM empleado
       INNER JOIN departamento
                  ON empleado.num_dep = departamento.numero_d;

-- 14. Obtener el total de los salarios, el salario máximo, el salario mínimo
-- y la media del salario de los empleados del departamento de ‘Investigación’.
SELECT sum(salario),
       max(salario),
       min(salario),
       avg(salario)
FROM empleado
       INNER JOIN departamento
                  ON empleado.num_dep = departamento.numero_d
WHERE departamento.nombre_d = 'Investigación';

-- 15 Número de empleados que tiene el departamento de ‘Investigación’.
SELECT count(*)
FROM empleado
       INNER JOIN departamento
                  ON empleado.num_dep = departamento.numero_d
WHERE departamento.nombre_d = 'Investigación';

-- 16. Número de salarios diferentes en la empresa.
SELECT count(DISTINCT salario)
FROM empleado;

-- 17. Seleccionar los NSS de los empleados que trabajan en el mismo proyecto
-- y con las mismas horas de trabajo que el empleado con NSS=‘28/342139’
SELECT DISTINCT nss_e
FROM trabaja_en
       INNER JOIN (
  SELECT cod_proy,
         horas
  FROM trabaja_en
  WHERE nss_e = '28/342139') nss28
                  ON trabaja_en.cod_proy = nss28.cod_proy AND trabaja_en.horas = nss28.horas;

-- 18. NSS, nombre y apellidos de los empleados que no tienen familiares dependientes de él
SELECT *
FROM empleado
       LEFT JOIN familiar
                 ON empleado.nss = familiar.nsse
WHERE familiar.nsse IS NULL;

-- 19. Nombre y  apellidos de los empleados que tengan 2 o más familiares a su cargo
SELECT nss
FROM empleado
       LEFT JOIN familiar
                 ON empleado.nss = familiar.nsse
GROUP BY nss
HAVING count(familiar.nombre) > 2;

-- 20. Por cada departamento que tenga más de 5 empleados,
-- obtener el nombre de departamento y el número de empleados que ganan más de 3000 euros.
SELECT num_dep,
       count(CASE
               WHEN salario > 3000
                 THEN 1 END) AS numero
FROM empleado e
GROUP BY num_dep
HAVING count(nss) > 5;

-- 21. Nombre y apellidos de los empleados
-- que tengan un salario mayor que cualquiera de los empleados del departamento 5
SELECT nombre,
       apellidos
FROM empleado
WHERE salario > (
  SELECT max(salario)
  FROM empleado
  WHERE num_dep = 5);

-- 22. Nombre y apellidos de los empleados que tienen familiares que tienen el mismo nombre
--  y sexo que él. (select anidada, combinación y  exists
SELECT nombre,
       apellidos
FROM empleado
WHERE exists(SELECT *
             FROM familiar
             WHERE nsse = nss
               AND familiar.nombre = empleado.nombre);

-- 23. Nombre de todos los empleados que trabajan en todos los proyectos del departamento 5.
SELECT nombre,
       apellidos
FROM empleado
       INNER JOIN
     (SELECT nss
      FROM empleado MINUS
      SELECT NSS_E NSS
      FROM (SELECT
        NSS NSS_E,
        COD_P COD_PROY
        FROM (
        SELECT NSS
        FROM EMPLEADO) E
        CROSS JOIN
        (
        SELECT COD_P
        FROM PROYECTO
        WHERE NUM_DEP = 5) P_DEP5 MINUS SELECT
        NSS_E,
        COD_PROY
        FROM TRABAJA_EN)) e_en_todo_proy_dep5
     ON empleado.nss = e_en_todo_proy_dep5.nss;

-- 24. Lista de todos los códigos de los proyectos en los que participa
-- un empleado que se apellide Silvano bien como trabajador
-- o como gerente del departamento al que pertenece el proyecto.
WITH e AS (SELECT nss
           FROM empleado
           WHERE apellidos = 'Silvano')
SELECT cod_proy cod_p
FROM trabaja_en
       INNER JOIN e
                  ON nss = nss_e
UNION
SELECT cod_p
FROM proyecto
       INNER JOIN departamento
                  ON proyecto.num_dep = departamento.numero_d
       INNER JOIN e
                  ON nss_gerente = nss;