create database prueba1;
use prueba1;

create table productos (
idProducto INT not null auto_increment primary key,
nombre varchar(40),
precio decimal
);

Insert into productos (nombre, precio) values
("LAPTOP", 3000),
("PC", 4000),
("MOUSE", 100),
("TECLADO", 150),
("MONITOR", 2000),
("MICROFONO", 350),
("AUDIFONOS", 450);

select *  from productos;

create table ventas (
idVenta INT not null auto_increment primary key,
idProducto int ,
foreign key (idProducto) references productos (idProducto),
cantidad int 
);

insert into ventas (idProducto, cantidad) values
(5, 8),
(1, 15),
(6, 13),
(6, 4),
(2, 3),
(5, 1),
(4, 5),
(2, 5),
(6, 2),
(1, 8);

select * from ventas;

#Traer todos los productos que tengan una venta.
Select distinct p. * FROM productos p
JOIN ventas v on p.idProducto = v.idProducto;

#Traer todos los productos que tengan ventas y la cantidad total de productos vendidos.
Select  p.idProducto,
	p.nombre, 
    SUM(v.cantidad) AS Cantidad_Total_Vendida
From productos p
JOIN ventas v
ON
	p.idProducto = v.idProducto
GROUP BY
	p.nombre, 
    p.idProducto;

# Traer todos los productos (independientemente de si tienen ventas o no) 
# y la suma total ($) vendida por producto.
Select  p.idProducto,
	p.nombre, 
    SUM(v.cantidad * p.precio) AS Total_Vendido
From productos p
LEFT JOIN ventas v
ON
	p.idProducto = v.idProducto
GROUP BY
	p.nombre, 
    p.idProducto;

