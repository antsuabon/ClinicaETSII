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
            <td><b><c:out value="${dashboard}"/></b></td>
        </tr>
        
        <tr>
            <th>Proporci�n de los diagn�sticos m�s frecuentes</th>
            <td><b><c:out value="${dashboard}"/></b></td>
        </tr>
      
        <tr>
            <th>Proporci�n de las consultas seg�n su tipo de alta</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Proporci�n de los medicamentos m�s prescritos</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Relaci�n entre el n�mero de pacientes que un m�dico tiene asociado y los servicios que ofrece</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Tiempo de espera medio</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Media de diagn�sticos por consultas</th>
            <td><c:out value="${dashboard}"/></td>
        </tr>
        
        <tr>
            <th>Edad media de los pacientes</th>
            <td><c:out value="${dashboard.averageAge}"/></td>
        </tr>
    
    </table>
    
</petclinic:layout>