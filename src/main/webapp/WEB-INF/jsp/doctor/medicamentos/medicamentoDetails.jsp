<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="doctor medicamentos">

	<h2>
		Medicamento:
		<c:out value="${medicamento.nombre}" />
	</h2>


	<table class="table table-striped">
		<tr>
			<th>Principios activos</th>
			<td><c:out value="${medicamento.nombre}" /></td>
		</tr>
		<tr>
			<th>Laboratorio titular</th>
			<td><c:out value="${medicamento.labtitular}" /></td>
		</tr>
		<tr>
			<th>Condiciones de prescripción</th>
			<td><c:out value="${medicamento.cpresc}" /></td>
		</tr>
		<tr>
			<th>Forma farmacéutica</th>
			<td><c:out value="${medicamento.formaFarmaceutica.nombre}" /></td>
		</tr>
		<tr>
			<th>Dosis</th>
			<td><c:out value="${medicamento.dosis}" /></td>
		</tr>
	</table>

	<table class="table">

		<tr>

			<td><h3>Principios activos</h3>
				<table class="table table-striped">
					<c:forEach items="${medicamento.principiosActivos}" var="principioActivo">

						<tr>
							<td><c:out value="${principioActivo.nombre}" /></td>
							<td><c:out value="${principioActivo.cantidad} ${principioActivo.unidad}" /></td>
						</tr>

					</c:forEach>
				</table></td>

			<td>
				<h3>Excipientes</h3>
				<table class="table table-striped">
					<c:forEach items="${medicamento.excipientes}" var="excipiente">

						<tr>
							<td><c:out value="${excipiente.nombre}" /></td>
							<td><c:out value="${excipiente.cantidad} ${excipiente.unidad}" /></td>
						</tr>

					</c:forEach>
				</table>
			</td>

		</tr>
		
		<table class="table">

		<tr>

			<td><h3>Vias de administración</h3>
				<table class="table table-striped">
					<c:forEach items="${medicamento.viasAdministracion}" var="viaAdministracion">

						<tr>
							<td><c:out value="${viaAdministracion.nombre}" /></td>
						</tr>

					</c:forEach>
				</table></td>

			<td>
				<h3>Presentaciones</h3>
				<table class="table table-striped">
					<c:forEach items="${medicamento.presentaciones}" var="presentacion">

						<tr>
							<td><c:out value="${presentacion.nombre}" /></td>
						</tr>

					</c:forEach>
				</table>
			</td>

		</tr>

	</table>



</petclinic:layout>