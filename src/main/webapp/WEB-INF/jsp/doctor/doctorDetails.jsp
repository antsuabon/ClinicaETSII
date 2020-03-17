<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor">

    <h2>Datos Personales</h2>

    <table class="table table-striped">
        <tr>
            <th>Nombre completo</th>
            <td><b><c:out value="${doctor.fullName}"/></b></td>
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
            <th>Teléfono</th>
            <td><c:out value="${doctor.phone}"/></td>
        </tr>
    </table>
	
	<br/>
	
	<h2>Datos Médicos</h2>

    <table class="table table-striped">
        <tr>
            <th>Número de colegiado</th>
            <td><c:out value="${doctor.collegiateCode}"/></td>
        </tr>
    </table>
    
    <br/>
	
	<h2>Cartera de Servicios</h2>
    
    <table class="table table-striped">
        <c:forEach var="service" items="${doctor.services}">

            <tr>
                <td><c:out value="${service.name}"/></td>
            </tr>

        </c:forEach>
    </table>
    
    <a href="/doctor/edit" class="btn btn-default">Editar perfil</a>

</petclinic:layout>
