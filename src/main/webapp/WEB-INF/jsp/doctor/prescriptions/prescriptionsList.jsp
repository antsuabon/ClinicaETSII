<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

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

            <th style="width: 150px;">Fecha de Inicio</th>

        

            <th style="width: 150px;">Fecha de Fin</th>
			<th></th>

        </tr>

        </thead>

        <tbody>

        

        

        <c:forEach items="${prescriptions}" var="prescription">

            <tr>

                <td>
					<c:out value="${prescription.startDate}"/>

                </td>

                <td>
					<c:out value="${prescription.endDate}"/>

                </td>
				
				<td>
				<spring:url value="/doctor/patients/{patientId}/prescriptions/{prescriptionId}" var="prescriptionUrl">
						<spring:param name="patientId" value="${patientId}"/>
                        <spring:param name="prescriptionId" value="${prescription.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(prescriptionUrl)}">Detalles</a>
				</td>
            
                

            </tr>

            

        </c:forEach>

        

        

          

        </tbody>

    </table>

    </c:otherwise>

     </c:choose> 

</petclinic:layout>