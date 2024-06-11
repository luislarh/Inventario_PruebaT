<%-- 
    Document   : mensaje
    Created on : 9 jun. 2024, 07:32:58
    Author     : luis
--%>
<% if (request.getAttribute("mensaje") != null) { %>
    <p><%= request.getAttribute("mensaje") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
<% } %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Error sdfsdf</h1>
    </body>
</html>
