<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="prescriptions">
	<h2>Lista de Prescripciones</h2>

	<c:choose>

		<c:when test="${emptyList=='true'}">
			<p>No se han encontrado prescripciones registradas para ese paciente</p>
		</c:when>

		<c:otherwise>

			<table id="prescriptionsTable" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 20%;">Fecha de Inicio</th>
						<th style="width: 20%;">Fecha de Fin</th>
						<th style="width: 40%;"></th>
						<th></th>
					</tr>
				</thead>

				<tbody>

					<c:forEach items="${prescriptions}" var="prescription">

						<tr>

							<td><spring:eval expression="prescription.startDate"/></td>
							<td><spring:eval expression="prescription.endDate"/></td>
							<td></td>

							<td><spring:url value="/doctor/patients/{patientId}/prescriptions/{prescriptionId}" var="prescriptionUrl">
									<spring:param name="patientId" value="${patientId}" />
									<spring:param name="prescriptionId" value="${prescription.id}" />
								</spring:url> <a href="${fn:escapeXml(prescriptionUrl)}">Ver prescripción</a></td>
						</tr>

					</c:forEach>

				</tbody>
			</table>
			
			<spring:url value="/doctor/patients/{patientId}/prescriptions/new" var="newPrescriptionUrl">
				<spring:param name="patientId" value="${patientId}" />
			</spring:url> <a class="btn btn-default" href="${fn:escapeXml(newPrescriptionUrl)}">Añadir prescripción</a>

		</c:otherwise>
	</c:choose>

</petclinic:layout>
