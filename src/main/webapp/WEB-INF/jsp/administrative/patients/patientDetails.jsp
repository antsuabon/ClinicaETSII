<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="patient">

    <h2>Detalles del paciente</h2>


    <table class="table table-striped">
        
        <tr>
            <th>Nombre de usuario</th>
            <td><b><c:out value="${patient.username}"/></b></td>
        </tr>
        <tr>
            <th>Nombre completo</th>
            <td><b><c:out value="${patient.name} ${patient.surname}"/></b></td>
        </tr>
        <tr>
            <th>Fecha de nacimiento</th>
            <td><petclinic:localDate date="${patient.birthDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>Dirección</th>
            <td><c:out value="${patient.address}"/></td>
        </tr>
        <tr>
            <th>Provincia</th>
            <td><c:out value="${patient.state}"/></td>
        </tr>
        <tr>
            <th>NSS</th>
            <td><c:out value="${patient.nss}"/></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${patient.dni}"/></td>
        </tr>
        <tr>
            <th>Correo electrónico</th>
            <td><c:out value="${patient.email}"/></td>
        </tr>
        <tr>
            <th>Teléfono principal</th>
            <td><c:out value="${patient.phone}"/></td>
        </tr>
        <tr>
            <th>Teléfono secudario</th>
            <td><c:out value="${patient.phone2}"/></td>
        </tr>
        <tr>
            <th>Médico de cabecera</th>
            <td><c:out value="${patient.generalPractitioner.fullName}"/></td>
        </tr>
    </table>


    
    
</petclinic:layout>