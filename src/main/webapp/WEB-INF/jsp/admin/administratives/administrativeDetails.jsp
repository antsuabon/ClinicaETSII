<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="administrative">

    <h2>Detalles del administrativo</h2>


    <table class="table table-striped">
        
        <tr>
            <th>Nombre de usuario</th>
            <td><b><c:out value="${administrative.username}"/></b></td>
        </tr>
        <tr>
            <th>Nombre completo</th>
            <td><b><c:out value="${administrative.name} ${administrative.surname}"/></b></td>
        </tr>
      
        <tr>
            <th>DNI</th>
            <td><c:out value="${administrative.dni}"/></td>
        </tr>
        <tr>
            <th>Correo electrónico</th>
            <td><c:out value="${administrative.email}"/></td>
        </tr>
        <tr>
            <th>Teléfono principal</th>
            <td><c:out value="${administrative.phone}"/></td>
        </tr>
    </table>
    
   	<spring:url value="/admin/administratives/{administrativeId}/delete" var="administrativeDeleteUrl">
       	<spring:param name="administrativeId" value="${administrative.id}"/>
  	</spring:url>
    <a href="${fn:escapeXml(administrativeDeleteUrl)}" class="btn btn-default">Eliminar Administrativo</a>
    
    
</petclinic:layout>