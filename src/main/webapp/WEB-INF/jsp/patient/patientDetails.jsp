<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="patients">
    <h2>Patient Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre completo</th>
            <td><b><c:out value="${patient.name} ${patient.surname}"/></b></td>
        </tr>
        <tr>
            <th>Fecha de nacimiento</th>
            <td><petclinic:localDate date="${patient.birthDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>Direcci�n</th>
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
            <th>Correo electr�nico</th>
            <td><c:out value="${patient.email}"/></td>
        </tr>
        <tr>
            <th>Tel�fono principal</th>
            <td><c:out value="${patient.phone}"/></td>
        </tr>
        <tr>
            <th>Tel�fono secundario</th>
            <td><c:out value="${patient.phone2}"/></td>
        </tr>
        <tr>
            <th>M�dico de cabecera</th>
            <td><c:out value="${patient.generalPractitioner.fullName}"/></td>
        </tr>
    </table>

    
    <a href="/patient/edit" class="btn btn-default">Edit Patient</a>
    
    
</petclinic:layout>