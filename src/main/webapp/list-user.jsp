<%-- 
    Document   : list-user
    Created on : 05/07/2019, 10:40:23 AM
    Author     : ulima
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>Lista de jugadores</title>
</head>
<body>
 <center>
  <h1>LIsta de jugadores</h1>
        <h2>
         <a href="new">Añadir jugador nuevo</a>
         &nbsp;&nbsp;&nbsp;
         <a href="list">Lista de jugadores</a>
         
        </h2>
 </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Equipo</th>
                <th>Pais</th>
                <th>Acciones</th>
            </tr>
            <c:forEach var="user" items="${listUser}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                    <td>
                     <a href="edit?id=<c:out value='${user.id}' />">Edit</a>
                     &nbsp;&nbsp;&nbsp;&nbsp;
                     <a href="delete?id=<c:out value='${user.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div> 
</body>
</html>