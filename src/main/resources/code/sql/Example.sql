CREATE TABLE grupo (
  cod_grupo CHAR(3) PRIMARY KEY,
  curso     CHAR(1) NOT NULL,
  turno     CHAR(1) DEFAULT 'M',
  CONSTRAINT manana_o_tarde CHECK (turno IN ('M', 'T')),
  CHECK (curso > '0' AND curso < '4')
);

SELECT *
FROM grupo;

CREATE TABLE alumno (
  num_mat   CHAR(3),
  nombre    VARCHAR2(20) UNIQUE,
  ciudad    CHAR(25) NOT NULL,
  cod_grupo CHAR(3),
  precio    NUMBER,
  PRIMARY KEY (num_mat),
  FOREIGN KEY (cod_grupo) REFERENCES grupo ON DELETE SET NULL
);

SELECT *
FROM alumno;

CREATE VIEW alumnos_madrid AS (
  SELECT *
  FROM alumno
  WHERE ciudad = 'Madrid'
);

SELECT *
FROM alumnos_madrid;

CREATE INDEX ind_alumno
  ON alumno (ciudad, cod_grupo);

INSERT INTO grupo
VALUES ('I21', '1', DEFAULT);
INSERT INTO grupo (curso, cod_grupo)
VALUES ('2', 'I22');

SELECT *
FROM grupo;

CREATE TABLE grupo_m AS
SELECT *
FROM grupo
WHERE 1 = 2;

INSERT INTO grupo_m
SELECT *
FROM grupo
WHERE turno = 'M';

SELECT *
FROM grupo_m;

CREATE TABLE grupo_curso1 AS
SELECT turno,
       cod_grupo codigo
FROM grupo
WHERE 1 = 2;
INSERT INTO grupo_curso1 (turno, codigo)
SELECT turno,
       cod_grupo
FROM grupo
WHERE curso = '1';

SELECT *
FROM grupo_curso1;

DELETE
FROM grupo
WHERE curso = '1';

SELECT *
FROM grupo;

DELETE
FROM grupo;

SELECT *
FROM grupo;

UPDATE alumno
SET cod_grupo
           = '8',
    precio = precio + 5000
WHERE nombre > 'RAMIRO';

SELECT *
FROM alumno;

SELECT nombre,
       ciudad
FROM alumno;

SELECT ciudad
FROM alumno
ORDER BY ciudad ASC;

SELECT ciudad
FROM alumno
ORDER BY nombre;

SELECT DISTINCT nombre,
                ciudad
FROM alumno
ORDER BY nombre;

SELECT nombre,
       precio * 0.10
FROM alumno
ORDER BY 2, nombre;

SELECT *
FROM alumno
WHERE cod_grupo = 'I21';

SELECT *
FROM alumno
WHERE cod_grupo LIKE '12%';

INSERT INTO alumno
VALUES (9, 'Eva', 'Fuenlabrada', NULL, 60000);

SELECT *
FROM alumno
WHERE cod_grupo IS NULL;

SELECT *
FROM alumno
WHERE precio BETWEEN 15000 AND 30000;

SELECT count(*)
FROM alumno
WHERE precio = 25000;

SELECT count(DISTINCT cod_grupo)
FROM alumno;

SELECT max(precio)
FROM alumno;

SELECT min(precio)
FROM alumno;

SELECT avg(precio) * 0.1
FROM alumno;

SELECT sum(precio)
FROM alumno;

SELECT ciudad,
       avg(precio)
FROM alumno
GROUP BY ciudad;

SELECT ciudad,
       avg(precio)
FROM alumno
WHERE num_mat < '5'
GROUP BY ciudad;

SELECT ciudad,
       avg(precio)
FROM alumno
WHERE num_mat < '5'
GROUP BY ciudad
HAVING avg(precio) > 20000;

CREATE TABLE empleado (
  dni    CHARACTER(6) PRIMARY KEY,
  nemp   VARCHAR2(20) UNIQUE,
  ciudad CHARACTER(63) NOT NULL
);

SELECT *
FROM empleado;

SELECT nombre,
       ciudad
FROM alumno
  MINUS
SELECT nemp,
       ciudad
FROM empleado;

SELECT nombre,
       ciudad
FROM alumno
UNION ALL
SELECT nemp,
       ciudad
FROM empleado;

SELECT nombre,
       ciudad
FROM alumno
INTERSECT
SELECT nemp,
       ciudad
FROM empleado;

SELECT *
FROM alumno
       CROSS JOIN grupo;

SELECT alumno.nombre,
       alumno.ciudad,
       grupo.curso
FROM alumno
       JOIN grupo
            ON alumno.cod_grupo = grupo.cod_grupo;

SELECT *
FROM alumno
       NATURAL JOIN grupo;

SELECT alumno.nombre,
       alumno.ciudad,
       grupo.curso
FROM alumno
       NATURAL JOIN grupo
WHERE turno = 'M';

SELECT g.curso,
       g.cod_grupo,
       a.nombre
FROM alumno a,
     grupo g
WHERE (a.cod_grupo = g.cod_grupo);

SELECT g.curso,
       g.cod_grupo,
       a.nombre
FROM alumno a
       RIGHT JOIN grupo g
                  ON a.cod_grupo = g.cod_grupo;

SELECT a.nombre,
       g.cod_grupo
FROM alumno a
       LEFT JOIN grupo g
                 ON a.cod_grupo = g.cod_grupo;

-- FULL JOIN
SELECT *
FROM alumno
       RIGHT OUTER JOIN grupo
                        ON alumno.cod_grupo = grupo.cod_grupo
UNION
SELECT *
FROM alumno
       LEFT OUTER JOIN grupo
                       ON alumno.cod_grupo = grupo.cod_grupo;

SELECT *
FROM alumno
WHERE cod_grupo IN (
  SELECT cod_grupo
  FROM grupo
  WHERE curso = '1');