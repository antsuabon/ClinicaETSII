<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="appointments">
    
    <h2>Proximas Citas</h2>

    <c:choose>

	<c:when test="${emptyListDelete=='true'}">

		<p>No se han encontrado consultas registradas para ese paciente</p>

	 </c:when>

	 <c:otherwise>

    <table id="consultationsTable" class="table table-striped">

        <thead>

        <tr>
        <th style="width: 25%;">Fecha de Inicio</th>
		<th style="width: 45%;">Fecha de Fin</th>
		<th></th>
		</tr>

        </thead>

        <tbody>

        <c:forEach items="${appointmentsDelete}" var="appointment">

            <tr>
				<td>
				<spring:eval expression="appointment.startTime"/>
				</td>

                <td>
				<spring:eval expression="appointment.endTime"/>
				</td>

				<td>
            	<spring:url value="/patient/appointments/{appointmentId}/delete" var="appointmentUrl">
                        <spring:param name="appointmentId" value="${appointment.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(appointmentUrl)}"><c:out value="Delete Appointment"/></a>
                </td>
                
             </tr>
             
        </c:forEach>
        
        </tbody>

    </table>
    

    </c:otherwise>

    </c:choose> 


<h2>Citas Pasadas</h2>

    <c:choose>

	<c:when test="${emptyListDone=='true'}">

		<p>No se han encontrado consultas registradas para ese paciente</p>

	 </c:when>

	 <c:otherwise>

    <table id="consultationsTable" class="table table-striped">

        <thead>

        <tr>
        <th style="width: 25%;">Fecha de Inicio</th>
		<th style="width: 60%;">Fecha de Fin</th>
		</tr>

        </thead>

        <tbody>

        <c:forEach items="${appointments}" var="appointment">

            <tr>
				<td>
				<spring:eval expression="appointment.startTime"/>
				</td>

                <td>
				<spring:eval expression="appointment.endTime"/>
				</td>

                
             </tr>
             
        </c:forEach>
        
        </tbody>

    </table>
    

    </c:otherwise>

    </c:choose> 

</petclinic:layout>