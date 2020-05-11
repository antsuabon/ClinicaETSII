<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="medicines">

    <h2>Medicamentos</h2>

	<c:choose>

	<c:when test="${emptylist=='true'}">

		<p>No se han encontrado resultados</p>

	 </c:when>

	 <c:otherwise>

    <table id="medicinesTable" class="table table-striped">

        <thead>

        <tr>

            <th style="width: 150px;">Nombre Comercial</th>

            <th style="width: 150px;">Nombre Genérico</th>
            
            <th style="width: 150px;">Indicaciones</th>

            <th style="width: 150px;">Contraindicaciones</th>

        </tr>

        </thead>

        <tbody>

           

        <c:forEach items="${medicines}" var="medicine">

            <tr>

                <td>
					<spring:url value="/administrative/medicines/{medicineId}" var="medicineUrl">
                        <spring:param name="medicineId" value="${medicine.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(medicineUrl)}"><c:out value="${medicine.genericalName}"/></a>
                  

                </td>
                
                 <td>
                    <c:out value="${medicine.commercialName}"/>

                </td>
                
                 <td>
                    <c:out value="${medicine.indications}"/>

                </td>
                
                 <td>
                    <c:out value="${medicine.contraindications}"/>

                </td>

            </tr>

            

        </c:forEach>

        </tbody>

    </table>

    </c:otherwise>

     </c:choose> 

</petclinic:layout>