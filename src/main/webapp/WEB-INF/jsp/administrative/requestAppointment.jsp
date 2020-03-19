<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<petclinic:layout pageName="appointments">




	<table id="table" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 20%;">Hora de inicio</th>
				<th style="width: 20%;">Médico</th>
				<th></th>
				<th></th>

			</tr>
		</thead>
		<tbody>
			<tr>

				<td><spring:eval expression="appointment.startTime" /></td>

				<td><c:out value="${appointment.patient.generalPractitioner.name} ${appointment.patient.generalPractitioner.surname}" /></td>

				<td><form:form class="form-horizontal" action="/administrative/patients/${patientId}/appointments/save">
						<input type="hidden" name="startTime" value="${appointment.getStartTime()}" />
						<input type="hidden" name="endTime" value="${appointment.getEndTime()}" />

						<input type="radio" id="prioridadAlta" name="priority" value="true">
						<label for="prioridadAlta"> Prioridad Alta</label>
						<br>

						<input type="radio" id="prioridadBaja" name="priority" value="false">
						<label for="prioridadBaja"> Prioridad Baja</label>
						<br>
						

						<button class="btn btn-default" type="submit">Solicitar cita</button>

					</form:form></td>

			</tr>
		</tbody>
	</table>





</petclinic:layout>
