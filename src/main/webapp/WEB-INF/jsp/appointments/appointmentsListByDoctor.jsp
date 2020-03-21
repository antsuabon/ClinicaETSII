<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <h2>Citas futuras o por finalizar</h2>

    <table id="ownersTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 10%;">Urgente</th>
        	
            <th style="width: 20%;">Hora de inicio</th>
            <th style="width: 20%;">Hora de fin</th>
            
            <th>Nombre del paciente</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appointments}" var="appointment">
            <tr>
            
            	<td>
                    <c:out value="${appointment.priority ? 'Sí' : 'No'}"/>
                </td>
                
                <td>
                    <c:out value="${appointment.startTime}"/>
                </td>
                
                <td>
                    <c:out value="${appointment.endTime}"/>
                </td>
                
                
                <td>
                    <c:out value="${appointment.patient.fullName}"/>
                </td>
                <td>
                    <spring:url value="/doctors/${doctorId}/consultations/new?appointmentId={appointmentId}" var="newAppointmentUrl">
                        <spring:param name="appointmentId" value="${appointment.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(newAppointmentUrl)}">Agregar Consulta</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
