<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>



<petclinic:layout pageName="doctor medicamentos">

	<h2>Medicamentos</h2>

	<c:choose>

		<c:when test="${emptyList=='true'}">

			<p>No se han encontrado resultados</p>

		</c:when>

		<c:otherwise>

			<table id="medicamentosTable" class="table table-striped">

				<thead>

					<tr>

						<th>Nombre</th>
						<th></th>

					</tr>

				</thead>

				<tbody>



					<c:forEach items="${consultaMedicamento.resultados}" var="medicamento">

						<tr>

							<td><c:out value="${medicamento.nombre}" /></td>

							<td><spring:url value="/doctor/medicamentos/{nregistro}" var="medicamentoUrl">
									<spring:param name="nregistro" value="${medicamento.nregistro}" />
								</spring:url> <a href="${fn:escapeXml(medicamentoUrl)}"><c:out value="Ver detalles" /></a></td>

						</tr>



					</c:forEach>

				</tbody>

			</table>

		</c:otherwise>

	</c:choose>

</petclinic:layout>