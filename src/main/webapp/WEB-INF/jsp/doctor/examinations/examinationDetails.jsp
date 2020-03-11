<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="examination">

    <h2>Detalles Examinación</h2>


    <table class="table table-striped">
        <tr>
            <th>Fecha de inicio</th>
            <td><c:out value="${examination.startTime}"/></td>
        </tr>

        <tr>
            <th>Descripción</th>
            <td><c:out value="${examination.Description}"/></td>
        </tr>

    </table>
    
    <br>

   
         


</petclinic:layout>
