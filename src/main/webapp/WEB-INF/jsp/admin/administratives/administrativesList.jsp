<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="administratives">
    <h2>Administrativos</h2>
	<c:choose>
	<c:when test="${emptyList=='true'}">
		<p>No se han encontrado resultados</p>
	 </c:when>
	 <c:otherwise>
    <table id="administrativesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 70%;">Nombre Completo</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        
    		<c:forEach var="administrative" items="${administratives}">
            <tr>
                <td>

                    <c:out value="${administrative.name} ${administrative.surname}"/>
                </td>
            
                
                <td>
                  <spring:url value="/admin/administratives/{administrativeId}" var="administrativeUrl">
                        <spring:param name="administrativeId" value="${administrative.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(administrativeUrl)}">Seleccionar Administrativo</a>
                
                </td>

                
            </tr>
            </c:forEach>           
        
          
        </tbody>
    </table>
    </c:otherwise>
     </c:choose> 
     
     
  
     
     
     
     
     
</petclinic:layout>
