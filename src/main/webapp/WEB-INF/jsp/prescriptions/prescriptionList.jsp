<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="prescriptions">
    <h2>Medicines</h2>

    <table id="prescriptionsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Medicamento</th>
            <th style="width: 150px;">Fecha de inicio</th>
            <th style="width: 150px;">Dosis</th>
            <th style="width: 150px;">Dias de duración</th>
            <th style="width: 150px;">Indicaciones</th>
            <th style="width: 150px;">Advertencias al paciente</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${prescriptions}" var="prescription">
            <tr>
                <td>
                    <spring:url value="/patient/medicines/{medicineId}" var="medicineUrl">
                        <spring:param name="medicineId" value="${prescription.medicine.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(medicineUrl)}"><c:out value="${prescription.medicine.genericalName} - ${prescription.medicine.commercialName}"/></a>
                </td>
                <td>
                    <c:out value="${prescription.startDate}"/>
                </td>
                <td>
                    <c:out value="${prescription.dosage}"/>
                </td>
                <td>
                    <c:out value="${prescription.days}"/>
                </td>
                  <td>
                    <c:out value="${prescription.pharmaceuticalWarning}"/>
                </td>
                  <td>
                    <c:out value="${prescription.patientWarning}"/>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
