<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor consultations">
    <h2>Lista de Citas por Atender</h2>

    <c:choose>

	<c:when test="${emptyList=='true'}">

		<p>No se han encontrado citas registradas</p>

	 </c:when>

	 <c:otherwise>

    <table id="patientsTable" class="table table-striped">

        <thead>

        <tr>

            <th style="width: 150px;">Nombre Completo</th>


        </tr>

        </thead>

        <tbody>

        <c:forEach items="${appointments}" var="appointment">

            <tr>

                <td>
					<spring:url value="/doctor/patients/{patientId}/consultations/new?appointmentId={appointmentId}" var="appointmentUrl">
                        <spring:param name="patientId" value="${appointment.patient.id}"/>
                        <spring:param name="appointmentId" value="${appointment.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(appointmentUrl)}"><c:out value="${appointment.patient.fullName}"/></a>

                </td>

            </tr>

        </c:forEach>


        </tbody>

    </table>

    </c:otherwise>

     </c:choose> 

</petclinic:layout>