# Inventario_PruebaT
 Evaluación Técnica de Programación
 ## Tecnologías Utilizadas
 ### IDE Utilizado
- **Nombre del IDE**: Apache NetBeans IDE
- **Versión**: 22

### Lenguaje de Programación
- **Lenguaje**: Java
- **Versión**: 17.0.7

 ### DBMS Utilizado

- **DBMS**: MySQL
- **Versión**: 8.0.25
  
## Pasos para Ejecutar la Aplicación

### Requisitos Previos

*Configurar la Base de Datos**:
    - Crear una base de datos en MySQL con el nombre `inventario`.
    - Ejecute el siguiente script SQL para crear las tablas necesarias:
    ```
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

    ```
    ### Pasos para Ejecutar

1. **Clonar el Repositorio**
2. **Importar el Proyecto en el IDE*
3. **Configurar el Archivo de conexion**:
    - En el archivo `conexion.java` configurar credenciales de la base de datos  correctas:
      ```
       private final String baseDatos = "inventario";
       private final String servidor = "jdbc:mysql://localhost:3310/" + baseDatos;
       private final String usuario = "root";
       private final String clave = "";
      ```
4. **Compilar y Ejecutar el Proyecto**
5. **Acceder a la Aplicación**:
    - Al abrir la aplicación se muestra el login
    - Los usuarios para el login son:
      ```
       Admin: email: luis@gmail.com password: 12345678
       Almacenista: email: juan@gmail.com password: 12345678
       Almacenista: email: lucas@gmail.com password: 1234
       ```
