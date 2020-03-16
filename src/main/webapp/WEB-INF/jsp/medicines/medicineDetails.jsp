<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="medicine">

    <h2>Detalles Medicamento</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre genérico</th>
            <td><c:out value="${medicine.genericalName}"/></td>
        </tr>
         <tr>
            <th>Nombre comercial</th>
            <td><c:out value="${medicine.commercialName}"/></td>
        </tr>
        <tr>
            <th>Cantidad</th>
            <td><c:out value="${medicine.quantity}"/></td>
        </tr>
        <tr>
            <th>Indicaciones</th>
            <td><c:out value="${medicine.indications}"/></td>
        </tr>
        <tr>
            <th>Contraindicaciones</th>
            <td><c:out value="${medicine.contraindications}"/></td>
        </tr>

    </table>
</petclinic:layout>