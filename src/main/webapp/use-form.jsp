<%-- 
    Document   : use-form
    Created on : 05/07/2019, 10:39:51 AM
    Author     : ulima
--%>

<<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>Lista de jugadores 2</title>
</head>
<body>
 <center>
  <h1>Jugadores</h1>
        <h2>
         <a href="new">Add New User</a>
         &nbsp;&nbsp;&nbsp;
         <a href="list">List All Users</a>
         
        </h2>
 </center>
    <div align="center">
  <c:if test="${user != null}">
   <form action="update" method="post">
        </c:if>
        <c:if test="${user == null}">
   <form action="insert" method="post">
        </c:if>
        <table border="4" cellpadding="5">
            <caption>
             <h2>
              <c:if test="${user != null}">
               Edit jugador
              </c:if>
              <c:if test="${user == null}">
               Añadir jugador
              </c:if>
             </h2>
            </caption>
          <c:if test="${user != null}">
           <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
          </c:if>            
            <tr>
                <th>Nombre: </th>
                <td>
                 <input type="text" name="name" size="45"
                   value="<c:out value='${user.name}' />"
                  />
                </td>
            </tr>
            <tr>
                <th>Equipo: </th>
                <td>
                 <input type="text" name="email" size="45"
                   value="<c:out value='${user.email}' />"
                 />
                </td>
            </tr>
            <tr>
                <th>Pais: </th>
                <td>
                 <input type="text" name="country" size="15"
                   value="<c:out value='${user.country}' />"
                 />
                </td>
            </tr>
            <tr>
             <td colspan="2" align="center">
              <input type="submit" value="Save" />
             </td>
            </tr>
        </table>
        </form>
    </div> 
</body>
</html>
