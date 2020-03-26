<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="appointments">



	<table id="table" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 75%;">Horas</th>
				<th></th>

			</tr>
		</thead>
		<tbody>

			<c:forEach items="${hours}" var="hora">
				<tr>
					<td><spring:eval expression="hora"/></td>

					<td><form:form class="form-horizontal" modelAttribute="appointment" method="get" action="/patient/appointments/new">
							<input type="hidden" name="fecha" value="${hora}" />
							<button class="btn btn-default" type="submit">Seleccionar hora</button>
						</form:form></td>

				</tr>
			</c:forEach>

		</tbody>
	</table>

</petclinic:layout>
