<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor patients">
    <h2>Lista de Pacientes</h2>

    <c:choose>

	<c:when test="${emptyList=='true'}">

		<p>No se han encontrado pacientes registrados</p>

	 </c:when>

	 <c:otherwise>

    <table id="patientsTable" class="table table-striped">

        <thead>

        <tr>

            <th style="width: 150px;">Nombre Completo</th>
			<th></th>

        </tr>

        </thead>

        <tbody>

        <c:forEach items="${patients}" var="patient">

            <tr>

                <td>
					<spring:url value="/doctor/patients/{patientId}/consultations" var="patientUrl">
                        <spring:param name="patientId" value="${patient.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(patientUrl)}"><c:out value="${patient.fullName}"/></a>

                </td>
                
                
                 <td>
					<spring:url value="/doctor/patients/{patientId}/prescriptions" var="patientUrl">
                        <spring:param name="patientId" value="${patient.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(patientUrl)}">Prescripciones del paciente</a>

                </td>
                

            </tr>

        </c:forEach>


        </tbody>

    </table>

    </c:otherwise>

     </c:choose> 

</petclinic:layout>