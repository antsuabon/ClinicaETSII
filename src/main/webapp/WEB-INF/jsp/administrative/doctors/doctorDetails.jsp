<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor">

    <h2>Detalles del médico</h2>


    <table class="table table-striped">
        
        <tr>
            <th>Nombre de usuario</th>
            <td><b><c:out value="${doctor.username}"/></b></td>
        </tr>
        <tr>
            <th>Nombre completo</th>
            <td><b><c:out value="${doctor.name} ${doctor.surname}"/></b></td>
        </tr>
      
        <tr>
            <th>Número de colegiado</th>
            <td><c:out value="${doctor.collegiateCode}"/></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${doctor.dni}"/></td>
        </tr>
        <tr>
            <th>Correo electrónico</th>
            <td><c:out value="${doctor.email}"/></td>
        </tr>
        <tr>
            <th>Teléfono principal</th>
            <td><c:out value="${doctor.phone}"/></td>
        </tr>
    
         <tr>
         
         		<th>Servicios</th>
                <td>
                    <c:forEach var="service" items="${doctor.services}">
                        <c:out value="${service.name} "/><br>
                    </c:forEach>
                </td>  
       
                
            </tr>
    </table>


    
    
</petclinic:layout>