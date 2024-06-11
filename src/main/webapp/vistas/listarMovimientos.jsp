<%-- 
    Document   : listarMovimientos
    Created on : 10 jun. 2024, 22:02:02
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Movimientos</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Lista de Movimientos</h1>

            <form action="MovimientoController" method="get">
                <div class="mb-3">
                    <label for="tipoMovimiento" class="form-label">Filtrar por tipo de movimiento:</label>
                    <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" name="tipoMovimiento" id="tipoMovimiento">
                        <option selected>Tipo de filtro</option>
                        <option value="">Todos</option>
                        <option value="Entrada">Entrada</option>
                        <option value="Salida">Salida</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Filtrar</button>
            </form>

            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Id Usuario</th>
                        <th>Id Producto</th>
                        <th>Tipo de movimiento</th>
                        <th>Cantidad</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="movimiento" items="${movimientos}">
                        <c:if test="${empty param.tipoMovimiento or movimiento.tipo_movimiento eq param.tipoMovimiento}">
                            <tr>
                                <td>${movimiento.id_movimiento}</td>
                                <td>${movimiento.id_usuario}</td>
                                <td>${movimiento.id_producto}</td>
                                <td>${movimiento.tipo_movimiento}</td>
                                <td>${movimiento.cantidad}</td>
                                <td>${movimiento.fecha_hora}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
            <!<!-- <a href="/ProductoController?accion=agregar" class="btn btn-primary">Agregar Producto</a> -->
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