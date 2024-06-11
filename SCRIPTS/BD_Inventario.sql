create database Inventario;
use Inventario;

CREATE TABLE Roles (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL
);

INSERT INTO Roles (nombre_rol) VALUES ('Administrador'), ('Almacenista');

select * from Roles;

CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_rol INT,
    estatus INT DEFAULT 1, -- 1  Activo, 0  Inactivo
    FOREIGN KEY (id_rol) REFERENCES Roles(id_rol)
);
select * from Usuarios;

CREATE TABLE Productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad INT DEFAULT 0,
    estatus INT DEFAULT 1 -- 1  Activo, 0  Inactivo
);

select *  from Productos;

CREATE TABLE Movimientos (
    id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_producto INT,
    tipo_movimiento ENUM('Entrada', 'Salida') NOT NULL,
    cantidad INT NOT NULL,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);
select * from Movimientos;




