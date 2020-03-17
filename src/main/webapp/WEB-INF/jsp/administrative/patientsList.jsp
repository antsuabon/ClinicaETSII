<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="patients">
	<h2>Pacientes</h2>
	<c:choose>
		<c:when test="${emptylist=='true'}">
			<p>No se han encontrado resultados</p>
		</c:when>
		<c:otherwise>
			<table id="patientsTable" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 150px;">Nombre Completo</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>


					<c:forEach items="${patients}" var="patient">
						<tr>
							<td><c:out value="${patient.fullName}" /></td>


							<td><spring:url value="/administrative/patients/appointments/{patientId}/table" var="patientUrl">
									<spring:param name="patientId" value="${patient.id}" />
								</spring:url> <a href="${fn:escapeXml(patientUrl)}">Crear Cita</a></td>


							<td>
						</tr>

					</c:forEach>



				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</petclinic:layout>
