<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctors">
    <h2>Médicos</h2>
	<c:choose>
	<c:when test="${emptyList=='true'}">
		<p>No se han encontrado resultados</p>
	 </c:when>
	 <c:otherwise>
    <table id="doctorsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Nombre Completo</th>
            <th style="width: 150px;">Servicios</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        
    		<c:forEach var="doctor" items="${doctors}">
            <tr>
                <td>

                    <c:out value="${doctor.name} ${doctor.surname}"/>
                </td>
                <td>
                    <c:forEach var="service" items="${doctor.services}">
                        <c:out value="${service.name} "/><br>
                    </c:forEach>
                </td>
                
                
                <td>
                  <spring:url value="/administrative/doctors/{doctorId}" var="doctorUrl">
                        <spring:param name="doctorId" value="${doctor.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(doctorUrl)}">Seleccionar Médico</a>
                
                </td>

                
            </tr>
            </c:forEach>           
        
          
        </tbody>
    </table>
    </c:otherwise>
     </c:choose> 
     
     
  
     
     
     
     
     
</petclinic:layout>
