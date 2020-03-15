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
            <th>Name</th>
            <td><b><c:out value="${patient.name} ${patient.surname}"/></b></td>
        </tr>
        <tr>
            <th>Birth Date</th>
            <td><petclinic:localDate date="${patient.birthDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>NSS</th>
            <td><c:out value="${patient.nss}"/></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${patient.address}"/></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${patient.email}"/></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${patient.dni}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${patient.phone}"/></td>
        </tr>
        <tr>
            <th>Telephone 2</th>
            <td><c:out value="${patient.phone2}"/></td>
        </tr>
    </table>

    <spring:url value="/patients/edit" var="editUrl"></spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Patient</a>
    
    
</petclinic:layout>