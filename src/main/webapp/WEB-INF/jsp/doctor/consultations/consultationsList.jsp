<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="consultations">
	<h2>Lista de Consultas</h2>

	<c:choose>

		<c:when test="${emptyList=='true'}">
			<p>No se han encontrado consultas registradas para ese paciente</p>
		</c:when>

		<c:otherwise>
		
			<table id="consultationsTable" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 20%;">Fecha de Inicio</th>
						<th style="width: 20%;">Fecha de Fin</th>
						<th style="width: 40%;"></th>
						<th></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${consultations}" var="consultation">

						<tr>

							<td><spring:eval expression="consultation.startTime" /></td>

							<td><spring:eval expression="consultation.endTime" /></td>

							<td></td>

							<td><spring:url value="/doctor/patients/{patientId}/consultations/{consultationId}" var="consultationUrl">
									<spring:param name="patientId" value="${patientId}" />
									<spring:param name="consultationId" value="${consultation.id}" />
								</spring:url> <a href="${fn:escapeXml(consultationUrl)}"><c:out value="Ver consulta" /></a></td>
						</tr>
						
					</c:forEach>
					
				</tbody>
			</table>

		</c:otherwise>
	</c:choose>

</petclinic:layout>