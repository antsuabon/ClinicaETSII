<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="doctor medicamentos">

	<script type="text/javascript">
		$(function(){
			$("#pactivos").autocomplete({
				source: "principiosActivosAutocomplete",
				minLength: 3
			});
			$("#labtitular").autocomplete({
				source: "laboratoriosAutocomplete",
				minLength: 3
			});
		});
	</script>

	<h2>Encontrar medicamentos</h2>

	<form:form modelAttribute="medicamento" action="/doctor/medicamentos" method="get" class="form-horizontal" id="search-medicamento-form">
		<div class="form-group">
			<div class="control-group">
				<label class="col-sm-2 control-label">Nombre </label>
				<div class="col-sm-10">
					<form:input class="form-control" path="nombre" size="30" maxlength="80" />
					<span class="help-inline"><form:errors path="*" /></span>
				</div>
			</div>

			<label class="col-sm-2 control-label">Principio activo </label>
			<div class="col-sm-10">
				<form:input class="form-control" path="pactivos" id="pactivos" size="30" maxlength="80" />
				<span class="help-inline"><form:errors path="*" /></span>
			</div>

			<label class="col-sm-2 control-label">Laboratorio </label>
			<div class="col-sm-10">
				<form:input class="form-control" path="labtitular" id="labtitular" size="30" maxlength="80" />
				<span class="help-inline"><form:errors path="*" /></span>
			</div>

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Encontrar medicamento</button>
			</div>
		</div>

	</form:form>


</petclinic:layout>