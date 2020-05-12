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
            <th>Número medio de prescripciones por médico</th>
            <td><b><c:out value="${dashboard}"/></b></td>
        </tr>
        
        <tr>
            <th>Proporción de los diagnósticos más frecuentes</th>
            <td><b><c:out value="${dashboard}"/></b></td>
        </tr>
      
        <tr>
            <th>Proporción de las consultas según su tipo de alta</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Proporción de los medicamentos más prescritos</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Relación entre el número de pacientes que un médico tiene asociado y los servicios que ofrece</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Tiempo de espera medio</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Media de diagnósticos por consultas</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Edad media de los pacientes</th>
            <td><c:out value="${dashboard.averageAge}"/></td>
        </tr>
    
    </table>
    
</petclinic:layout>