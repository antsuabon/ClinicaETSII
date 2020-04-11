<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="doctor patients">
	<h2>Lista de Pacientes del Centro</h2>

	<c:choose>

		<c:when test="${emptyList=='true'}">

			<p>No se han encontrado pacientes registrados</p>

		</c:when>

		<c:otherwise>

			<table id="patientsTable" class="table table-striped">

				<thead>

					<tr>

						<th style="width: 60%;">Nombre Completo</th>
						<th style="width: 15%;"></th>
					</tr>

				</thead>

				<tbody>

					<c:forEach items="${patients}" var="patient">

						<tr>

							<td><c:out value="${patient.fullName}" /></td>

								<td><spring:url value="/admin/patients/{patientId}" var="patientUrl">
									<spring:param name="patientId" value="${patient.id}" />
								</spring:url> <a href="${fn:escapeXml(patientUrl)}">Ver Detalles</a></td>

						</tr>

					</c:forEach>


				</tbody>

			</table>

		</c:otherwise>

	</c:choose>

</petclinic:layout>