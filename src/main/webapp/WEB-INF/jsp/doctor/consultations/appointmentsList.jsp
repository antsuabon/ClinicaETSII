<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor appointments">
    <h2>Lista de Citas por Atender</h2>

    <c:choose>

	<c:when test="${emptyList=='true'}">

		<p>No se han encontrado citas por atender registradas</p>

	 </c:when>

	 <c:otherwise>

    <table id="appointmentsTable" class="table table-striped">

        <thead>

        <tr>

			<th>Hora de inicio</th>
			<th>Hora de fin</th>
			<th style="width: 40%;">Nombre Completo</th>
			<th></th>

        </tr>

        </thead>

        <tbody>

        <c:forEach items="${appointments}" var="appointment">

            <tr>
            
            	<td><spring:eval expression="appointment.startTime"/></td>
            	<td><spring:eval expression="appointment.endTime"/></td>
				<td><c:out value="${appointment.surname}, ${appointment.name}"/></td>
                <td>
					<spring:url value="/doctor/patients/{patientId}/consultations/new?appointmentId={appointmentId}" var="appointmentUrl">
                        <spring:param name="patientId" value="${appointment.patientId}"/>
                        <spring:param name="appointmentId" value="${appointment.appointmentId}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(appointmentUrl)}">Añadir consulta</a>

                </td>

            </tr>

        </c:forEach>


        </tbody>

    </table>

    </c:otherwise>

     </c:choose> 

</petclinic:layout>