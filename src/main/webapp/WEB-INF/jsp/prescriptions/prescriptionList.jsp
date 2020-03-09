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
            <th style="width: 150px;">Fecha de inicio</th>
            <th style="width: 150px;">Dosis</th>
            <th style="width: 150px;">Dias de duración</th>
            <th style="width: 150px;">Indicaciones</th>
            <th style="width: 150px;">Advertencias al paciente</th>
            <th style="width: 150px;">Medicamento</th>
            <th style="width: 150px;">Paciente</th>
            <th style="width: 150px;">Doctor</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${prescriptions}" var="prescription">
            <tr>
                <td>
                    <spring:url value="/prescriptions/{prescriptionId}" var="prescriptionsUrl">
                        <spring:param name="medicinesId" value="${medicines.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(prescriptionsUrl)}"><c:out value="${prescriptions.genericalName} ${prescriptions.commercialName}"/></a>
                </td>
                <td>
                    <c:out value="${prescriptions.start_date}"/>
                </td>
                <td>
                    <c:out value="${prescriptions.dosage}"/>
                </td>
                <td>
                    <c:out value="${prescriptions.days}"/>
                </td>
                  <td>
                    <c:out value="${prescriptions.pharmaceutical_warning}"/>
                </td>
                  <td>
                    <c:out value="${prescriptions.patient_warning}"/>
                </td>
                 <td>
                    <c:out value="${prescriptions.medicine_id}"/>
                </td>
                 <td>
                    <c:out value="${prescriptions.patient_id}"/>
                </td>
                 <td>
                    <c:out value="${prescriptions.doctor_id}"/>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
