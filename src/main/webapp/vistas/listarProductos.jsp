<%-- 
    Document   : listarProductos.jsp
    Created on : 9 jun. 2024, 19:18:38
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") != null || session.getAttribute("almacenista") != null) {
%>

<% if (request.getAttribute("mensaje") != null) {%>
<div class="alert alert-success">
    <%= request.getAttribute("mensaje").toString()%>
</div>
<% }
    if (request.getAttribute("error") != null) {%>
<div class="alert alert-danger">
    <%= request.getAttribute("error").toString()%>
</div>
<% } %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar productos Activos</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Lista de Productos</h1>
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Estatus</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="producto" items="${productos}">
                        <tr>
                            <td>${producto.id_producto}</td>
                            <td>${producto.nombre_producto}</td>
                            <td>${producto.descripcion}</td>
                            <td>${producto.precio}</td>
                            <%
                                if (session.getAttribute("almacenista") != null
                                        && session.getAttribute("usuario") == null) {
                            %>
                            <td>${producto.cantidad}</td>
                            <%
                                }
                            %>
                            <%
                                if (session.getAttribute("usuario") != null
                                        && session.getAttribute("almacenista") == null) {
                            %>
                            <td>
                                <form action="ProductoController" method="post">
                                    <input type="hidden" name="accion" value="actualizarCantidad">
                                    <input type="hidden" name="id" value="${producto.id_producto}">
                                    <input type="number" name="cantidad" value="${producto.cantidad}" min="0">
                                    <button type="submit" class="btn btn-primary btn-sm">Actualizar</button>
                                </form>
                            </td>

                            <td>${producto.estatus}</td>
                            <td>
                                <a href="ProductoController?accion=cambiarEstatus&id=${producto.id_producto}&estatus=${producto.estatus == 1 ? 0 : 1}" class="btn btn-${producto.estatus == 1 ? 'danger' : 'success'} btn-sm">
                                    ${producto.estatus == 1 ? 'Dar de Baja' : 'Activar'}
                                </a>
                            </td>
                            <%
                                }
                            %>
                            <%
                                if (session.getAttribute("almacenista") != null
                                        && session.getAttribute("usuario") == null) {
                            %>
                            <td>${producto.estatus}</td>
                            <%
                                }
                            %>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <%
                if (session.getAttribute("usuario") != null
                        && session.getAttribute("almacenista") == null) {
            %>
            <a href="/ProductoController?accion=agregar" class="btn btn-primary">Agregar Producto</a>
            <%
                }
            %>
            <!--<a href="index.jsp" class="btn btn-secondary">Volver al inicio</a>-->
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
<%
    } else {
        response.sendRedirect("identificar.jsp");
    }
%>

