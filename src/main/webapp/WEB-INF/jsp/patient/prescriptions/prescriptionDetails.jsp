<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="prescription">

    <h2>Detalles de la prescripción</h2>

    <table class="table table-striped">
        <tr>
            <th>Fecha de inicio</th>
            <td><spring:eval expression="prescription.startDate"/></td>
        </tr>
         <tr>
            <th>Fecha de fin</th>
            <td><spring:eval expression="prescription.endDate"/></td>
        </tr>
        <tr>
            <th>Días</th>
            <td><c:out value="${prescription.days}"/></td>
        </tr>
        <tr>
            <th>Dosificación</th>
            <td><c:out value="${prescription.dosage}"/></td>
        </tr>
        <tr>
            <th>Número de dosis</th>
            <td><c:out value="${prescription.getNumDoses()}"/></td>
        </tr>
         <tr>
            <th>Advertencia al Farmacéutico</th>
            <td><c:out value="${prescription.pharmaceuticalWarning}"/></td>
        </tr>
         <tr>
            <th>Advertencia al Paciente</th>
            <td><c:out value="${prescription.patientWarning}"/></td>
        </tr>

    </table>
    
    <br>             
             
           <h3>Detalles del Medicamento</h3>
      <table class="table table-striped">
   		<tr>
   		  <th>Nombre Comercial</th>
            <td><c:out value="${prescription.medicine.commercialName}"/></td>
   		</tr>
   		<tr>
   		<th>Nombre Genérico</th>
            <td><c:out value="${prescription.medicine.genericalName}"/></td>
   		</tr>
   		<tr>
   		<th>Indicaciones</th>
            <td><c:out value="${prescription.medicine.indications}"/></td>
   		</tr>
             </table>


</petclinic:layout>