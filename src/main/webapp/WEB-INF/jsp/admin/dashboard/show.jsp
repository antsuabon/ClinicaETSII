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
            <td width="50%"><canvas id="mostFrequentDiagnoses"></canvas></td>
            <td width="50%"><canvas id="numberOfConsultationsByDischargeType"></canvas></td>
        </tr>
      
        <tr>
            <td width="50%"><canvas id="mostFrequestMedicines"></canvas></td>
            <td width="50%"><canvas id="ratioServicesPatients"></canvas></td>
        </tr>
        
    </table>
    
    <table class="table table-striped">
        
        <tr>
            <th>Número medio de prescripciones por médico</th>
            <td><b><c:out value="${dashboard.averageNumberOfPrescriptionsByDoctor}"/></b></td>
        </tr>
        
        <tr>
            <th>Tiempo de espera medio</th>
            <td><c:out value="${dashboard.averageWaitingTime}"/></td>
        </tr>
        
        <tr>
            <th>Media de diagnósticos por consultas</th>
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
			
			var mostFrequentDiagnosesLabels = [];
			
			<c:forEach items="${dashboard.mostFrequentDiagnosesLabels}" var="label">
	        
				mostFrequentDiagnosesLabels.push('<c:out value="${label}"/>');
        
        	</c:forEach>
			
			var mostFrequentDiagnosesChart = new Chart(mostFrequentDiagnoses, {
			    // The type of chart we want to create
			    type: 'polarArea',

			    // The data for our dataset
			    data: {
			        labels: mostFrequentDiagnosesLabels,
			        datasets: [{
			            //label: 'Nu',
			            backgroundColor: [
			            	'rgb(255, 99, 132)',
			            	'rgb(124,252,0)',
			            	'rgb(0,255,255)',
			            	'rgb(0,255,255)',
			            	'rgb(250,128,114)'
			            ],
			            //borderColor: 'rgb(255, 99, 132)',
			            data: <c:out value="${dashboard.mostFrequentDiagnosesValues}"/>
			        }]
			    },

			    	// Configuration options go here
			    	options: {
			    		title: {
			    			display:true,
			    			text: 'Proporción de los diagnósticos más frecuentes'
			    		},
			    		legend: {
			    	        display: false
			    	    }
			    	}
			});
			
			var numberOfConsultationsByDischargeType = document.getElementById('numberOfConsultationsByDischargeType').getContext('2d');
			
			var numberOfConsultationsByDischargeTypeLabels = [];
			
			<c:forEach items="${dashboard.numberOfConsultationsByDischargeTypeLabels}" var="label">
	        
				numberOfConsultationsByDischargeTypeLabels.push('<c:out value="${label}"/>');
        
        	</c:forEach>
			
			
			var numberOfConsultationsByDischargeTypeChart = new Chart(numberOfConsultationsByDischargeType, {
			    // The type of chart we want to create
			    type: 'pie',

			    // The data for our dataset
			    data: {
			        labels: numberOfConsultationsByDischargeTypeLabels,
			        datasets: [{
			            //label: 'Número de consultas',
			            backgroundColor: [
			            	'rgb(255, 99, 132)',
			            	'rgb(124,252,0)',
			            	'rgb(0,255,255)',
			            	'rgb(0,255,255)',
			            	'rgb(250,128,114)'
			            ],
			            //borderColor: 'rgb(255, 99, 132)',
			            data: <c:out value="${dashboard.numberOfConsultationsByDischargeTypeValues}"/>
			        }]
			    },

			    	// Configuration options go here
			    	options: {
			    		title: {
			    			display:true,
			    			text: 'Proporción de las consultas según su tipo de alta'
			    		},
			    		legend: {
			    	        display: false
			    	    }
			    	}
			});
			
			var mostFrequestMedicines = document.getElementById('mostFrequestMedicines').getContext('2d');
			
			var mostFrequestMedicinesLabels = [];
			
			<c:forEach items="${dashboard.mostFrequestMedicinesLabels}" var="label">
	        
				mostFrequestMedicinesLabels.push('<c:out value="${label}"/>');
        
        	</c:forEach>
			
			
			var mostFrequestMedicinesChart = new Chart(mostFrequestMedicines, {
			    // The type of chart we want to create
			    type: 'bar',

			    // The data for our dataset
			    data: {
			        labels: mostFrequestMedicinesLabels,
			        datasets: [{
			            //label: 'My First dataset',
			            backgroundColor: 'rgb(255, 99, 132)',
			            //borderColor: 'rgb(255, 99, 132)',
			            data: <c:out value="${dashboard.mostFrequestMedicinesValues}"/>
			        }]
			    },

			    	// Configuration options go here
			    	options: {
			    		title: {
			    			display:true,
			    			text: 'Proporción de los medicamentos más prescritos'
			    		},
			    		legend: {
			    	        display: false
			    	    },
			    		scales: {
			    		    xAxes: [{
			    		      scaleLabel: {
			    		        display: true,
			    		        labelString: 'Número de veces prescrito'
			    		      }
			    		    }],
			    		    yAxes: [{
				    		      scaleLabel: {
				    		        display: true,
				    		        labelString: 'Medicamento'
				    		      }
				    		    }]
			    		  } 
			    	}
			});
			
			var ratioServicesPatients = document.getElementById('ratioServicesPatients').getContext('2d');
			
			var ratioServicesPatientsNumServices = [];
			
			<c:forEach items="${dashboard.ratioServicesPatientsNumServices}" var="label">
	        
			ratioServicesPatientsNumServices.push('<c:out value="${label}"/>');
        
        	</c:forEach>
			
			
			var ratioServicesPatientsChart = new Chart(ratioServicesPatients, {
			    // The type of chart we want to create
			    type: 'line',

			    // The data for our dataset
			    data: {
			        labels: ratioServicesPatientsNumServices,
			        datasets: [{
			            //label: 'My First dataset',
			            backgroundColor: 'rgb(255,140,0,0.6)',
			            borderColor: 'rgb(255,140,0)',
			            data: <c:out value="${dashboard.ratioServicesPatientsAvgPatients}"/>
			        }]
			    },

			    	// Configuration options go here
			    	options: {
			    		title: {
			    			display:true,
			    			text: 'Relación entre el número de pacientes que un médico tiene asociado y los servicios que ofrece'
			    		},
			    		legend: {
			    	        display: false
			    	    },
			    		scales: {
			    		    xAxes: [{
			    		      scaleLabel: {
			    		        display: true,
			    		        labelString: 'Número de servicios'
			    		      }
			    		    }],
			    		    yAxes: [{
				    		      scaleLabel: {
				    		        display: true,
				    		        labelString: 'Media de pacientes'
				    		      }
				    		    }]
			    		  } 
			    	}
			});
	    }
	    
    </script>
    
</petclinic:layout>