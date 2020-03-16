<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="doctors">
   
       
     
         <table id="table" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Horas</th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        
          
             
     <c:forEach items="${hours}" var="horas">
           <tr>
               
                 <td><c:out value="${formato.format(horas)}" /> </td>
                 
                 <td>
                   <spring:url value="/appointment/new?fecha={fecha}" var="appointmentUrl">
                        <spring:param name="doctorId" value="${doctor}"/>
                        <spring:param name="fecha" value="${horas}"/>
                       
                    </spring:url>
         			<a href="${fn:escapeXml(appointmentUrl)}">Seleccionar hora</a>
                 </td>
                
               
        
                
            </tr> 
        </c:forEach>
        
        </tbody>
    </table>
     
     
     
     
     
     
</petclinic:layout>
