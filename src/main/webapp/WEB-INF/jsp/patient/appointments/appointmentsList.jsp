<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="appointments">
    <h2>Lista de Citas</h2>

    <c:choose>

	<c:when test="${emptyList=='true'}">

		<p>No se han encontrado consultas registradas para ese paciente</p>

	 </c:when>

	 <c:otherwise>

    <table id="consultationsTable" class="table table-striped">

        <thead>

        <tr>

            <th style="width: 150px;">Fecha de Inicio</th>

        

            <th style="width: 150px;">Fecha de Fin</th>


        </tr>

        </thead>

        <tbody>

        

        

        <c:forEach items="${appointments}" var="appointment">

            <tr>

                <td>
					<c:out value="${appointment.startTime}"/>

                </td>

                <td>
					<c:out value="${appointment.endTime}"/>

                </td>

            
                

            </tr>

            

        </c:forEach>

        

        

          

        </tbody>

    </table>

    </c:otherwise>

     </c:choose> 

</petclinic:layout>