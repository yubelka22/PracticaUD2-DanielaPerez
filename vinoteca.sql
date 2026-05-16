CREATE DATABASE IF NOT EXISTS vinoteca;
USE vinoteca;

CREATE TABLE IF NOT EXISTS bodegas (
    idbodega INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS vinos (
    idvino INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    añada INT NOT NULL,
    origen VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    idbodega INT NOT NULL,
    fechaingreso DATE NOT NULL,
    FOREIGN KEY (idbodega) REFERENCES bodegas(idbodega)
);

CREATE TABLE IF NOT EXISTS empleados (
    idempleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    telefono VARCHAR(20),
    salario DECIMAL(10,2) NOT NULL,
    fechacontratacion DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS ventas (
    idventa INT AUTO_INCREMENT PRIMARY KEY,
    idempleado INT NOT NULL,
    idvino INT NOT NULL,
    nombrecliente VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    metodopago VARCHAR(50) NOT NULL,
    fechaventa DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idempleado) REFERENCES empleados(idempleado),
    FOREIGN KEY (idvino) REFERENCES vinos(idvino)
);

INSERT INTO bodegas (nombre, pais, telefono, email, direccion) VALUES
('Bodega Marques de Riscal', 'Espana', '945600000', 'info@marquesderiscal.com', 'Calle Torrea 1, Elciego'),
('Bodega Torres', 'Espana', '938177400', 'info@bodegastorres.com', 'Calle Comercio 22, Vilafranca'),
('Antinori', 'Italia', '0552359700', 'info@antinori.it', 'Piazza degli Antinori 3, Florencia');

INSERT INTO empleados (nombre, apellidos, dni, cargo, telefono, salario, fechacontratacion) VALUES
('Ana', 'Garcia Lopez', '12345678A', 'gerente', '600111222', 2500.00, '2020-01-15'),
('Carlos', 'Martinez Ruiz', '87654321B', 'sumiller', '600333444', 1800.00, '2021-03-10');