<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor">

    <h2>Dashboard</h2>
    
     <table class="table table-striped">
        
        <tr>
            <th>N�mero medio de prescripciones por m�dico</th>
            <td><b><c:out value="${dashboard.averageNumberOfPrescriptionsByDoctor}"/></b></td>
        </tr>
        
        <tr>
            <th>Proporci�n de los diagn�sticos m�s frecuentes</th>
            
           
            <td>
             <canvas id="mostFrequentDiagnoses"></canvas><br>
             <b><c:out value="${dashboard.mostFrequentDiagnosesLabels}"/></b><br>
            <b><c:out value="${dashboard.mostFrequentDiagnosesValues}"/></b></td>
        </tr>
      
        <tr>
            <th>Proporci�n de las consultas seg�n su tipo de alta</th>
            
            
            <td>
            <canvas id="numberOfConsultationsByDischargeType"></canvas><br>
            <c:out value="${dashboard.numberOfConsultationsByDischargeTypeLabels}"/><br>
            <c:out value="${dashboard.numberOfConsultationsByDischargeTypeValues}"/></td>
        </tr>
        
        <tr>
            <th>Proporci�n de los medicamentos m�s prescritos</th>
            
            
            <td>
            <canvas id="mostFrequestMedicines"></canvas><br>
            <c:out value="${dashboard.mostFrequestMedicinesLabels}"/><br>
            <c:out value="${dashboard.mostFrequestMedicinesValues}"/></td>
        </tr>
        
        <tr>
            <th>Relaci�n entre el n�mero de pacientes que un m�dico tiene asociado y los servicios que ofrece</th>
            
            
            <td>
            <canvas id="ratioServicesPatients"></canvas><br>
            <c:out value="${dashboard.ratioServicesPatientsNumServices}"/><br>
            <c:out value="${dashboard.ratioServicesPatientsAvgPatients}"/>
            </td>
        </tr>
        
        <tr>
            <th>Tiempo de espera medio</th>
            <td><c:out value="${dashboard.averageWaitingTime}"/></td>
        </tr>
        
        <tr>
            <th>Media de diagn�sticos por consultas</th>
            <td><c:out value="${dashboard.averageDiagnosesPerConsultation}"/></td>
        </tr>
        
        <tr>
            <th>Edad media de los pacientes</th>
            <td><c:out value="${dashboard.averageAge}"/></td>
        </tr>
    
    </table>
    
    <script type="text/javascript">
	    window.onload = function() {
	    	var mostFrequentDiagnoses = document.getElementById('mostFrequentDiagnoses').getContext('2d');
			window.myPie = new Chart(ctx, config);
	    }
    </script>
    
</petclinic:layout>