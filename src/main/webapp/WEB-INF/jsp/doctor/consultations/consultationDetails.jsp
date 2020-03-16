<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="consultation">

    <h2>Detalles Consulta</h2>


    <table class="table table-striped">
        <tr>
            <th>Fecha de inicio</th>
            <td><c:out value="${consultation.startTime}"/></td>
        </tr>
         <tr>
            <th>Fecha de fin</th>
            <td><c:out value="${consultation.endTime}"/></td>
        </tr>
        <tr>
            <th>Anamnesis</th>
            <td><c:out value="${consultation.anamnesis}"/></td>
        </tr>
        <tr>
            <th>Observaciones</th>
            <td><c:out value="${consultation.remarks}"/></td>
        </tr>
        <tr>
            <th>Alta</th>
            <td><c:out value="${consultation.dischargeType.name}"/></td>
        </tr>

    </table>
    
    <spring:url value="{consultationId}/edit" var="editUrl">
        <spring:param name="consultationId" value="${consultation.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Editar Consulta</a>
    
    <br>

    <h3>Examinaciones</h3>
      <table class="table table-striped">
    <c:forEach items="${consultation.examinations}" var="examination">

            <tr>
                <td>
					<c:out value="${examination.description}"/>

                </td>


            </tr>
            </c:forEach>
             </table>
             <br>

    <h3>Diagnósticos</h3>
              <table class="table table-striped">
    <c:forEach items="${consultation.diagnoses}" var="diagnosis">

            <tr>
                <td>
					<c:out value="${diagnosis.name}"/>

                </td>


            </tr>
            </c:forEach>
             </table>
             <br>

    <h3>Constantes</h3>
              <table class="table table-striped">
    <c:forEach items="${consultation.constants}" var="constant">

            <tr>
                <td>
                <c:out value="${constant.constantType.name}"/>
					

                </td>

                <td>
					<c:out value="${constant.value}"/>

                </td>


            </tr>
            </c:forEach>
             </table>


</petclinic:layout>
