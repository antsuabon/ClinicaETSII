<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="consultation">

	<h2>Detalles Consulta</h2>


	<table class="table table-striped">
		<tr>
			<th>Fecha y hora de inicio</th>
			<td><spring:eval expression="consultation.startTime"></spring:eval></td>
		</tr>
		<tr>
			<th>Fecha y hora de fin</th>
			<td><spring:eval expression="consultation.endTime"></spring:eval></td>
		</tr>
		<tr>
			<th>Anamnesis</th>
			<td><c:out value="${consultation.anamnesis}" /></td>
		</tr>
		<tr>
			<th>Observaciones</th>
			<td><c:out value="${consultation.remarks}" /></td>
		</tr>
		<tr>
			<th>Tipo de alta</th>
			<td><c:out value="${consultation.dischargeType.name}" /></td>
		</tr>

	</table>

	<spring:url value="{consultationId}/edit" var="editUrl">
		<spring:param name="consultationId" value="${consultation.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Consulta</a>

            <tr>
                <td>
                <spring:url value="/doctor/patients/{patientId}/consultations/{consultationId}/examinations/{examinationId}" var="consultationUrl">
						<spring:param name="patientId" value="${patientId}"/>
                        <spring:param name="consultationId" value="${consultation.id}"/>
                         <spring:param name="examinationId" value="${examination.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(consultationUrl)}"><c:out value="${examination.description}"/></a>
					<br/>

	<h3>Examinaciones</h3>
	<table class="table table-striped">
		<c:forEach items="${consultation.examinations}" var="examination">

			<tr>
				<td><spring:eval expression="consultation.date"></spring:eval></td>
				<td><c:out value="${examination.description}" /></td>


			</tr>
		</c:forEach>
	</table>
	<br>

	<h3>Diagnósticos</h3>
	<table class="table table-striped">
		<c:forEach items="${consultation.diagnoses}" var="diagnosis">

			<tr>
				<td><c:out value="${diagnosis.name}" /></td>
			</tr>
		</c:forEach>
	</table>

	<spring:url value="{consultationId}/edit" var="editUrl">
		<spring:param name="consultationId" value="${consultation.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Consulta</a>

	<tr>
		<td><spring:url value="/doctor/patients/{patientId}/consultations/{consultationId}/examinations/{examinationId}"
				var="consultationUrl">
				<spring:param name="patientId" value="${patientId}" />
				<spring:param name="consultationId" value="${consultation.id}" />
				<spring:param name="examinationId" value="${examination.id}" />
			</spring:url> <a href="${fn:escapeXml(consultationUrl)}"><c:out value="${examination.description}" /></a> <br> <br>

			<h3>Exploraciones</h3>


			<table class="table table-striped">
				<c:forEach items="${consultation.examinations}" var="examination">

					<tr>
						<td width="25%" ><spring:eval expression="examination.startTime"></spring:eval></td>
						<td><c:out value="${examination.description}" /></td>
					</tr>
				</c:forEach>
			</table>
			<spring:url value="/doctor/patients/{patientId}/consultations/{consultationId}/examinations/new" var="newExaminationUrl">
				<spring:param name="patientId" value="${patientId}" />
				<spring:param name="consultationId" value="${consultation.id}" />
			</spring:url> <a class="btn btn-default" href="${fn:escapeXml(newExaminationUrl)}">Añadir exploración</a> <br> <br>

			<h3>Constantes</h3>

			<table class="table table-striped">
				<c:forEach items="${consultation.constants}" var="constant">

					<tr>
						<td><c:out value="${constant.constantType.name}" /></td>
						<td><c:out value="${constant.value}" /></td>
					</tr>
				</c:forEach>
			</table>

			<spring:url value="{consultationId}/constants/new" var="editUrl">
				<spring:param name="consultationId" value="${consultation.id}" />
			</spring:url> <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Añadir Constante</a> <br />
</petclinic:layout>
