
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="administrative new patient">

	<script>
		$(function() {
			$("#birthDate").datepicker({
				dateFormat : 'dd/mm/yy'
			});
		});
	</script>

	<h2>Nuevo Paciente</h2>
	<form:form modelAttribute="patient" class="form-horizontal" id="add-patient-form">
		<div class="form-group has-feedback">

			<petclinic:inputField label="Nombre de usuario" name="username" />
			<petclinic:inputField label="Nombre" name="name" />
			<petclinic:inputField label="Apellidos" name="surname" />
			<petclinic:inputField label="Fecha de nacimiento" name="birthDate" />
			<petclinic:inputField label="Dirección" name="address" />
			<petclinic:inputField label="Provincia" name="state" />
			<petclinic:inputField label="NSS" name="nss" />
			<petclinic:inputField label="DNI" name="dni" />
			<petclinic:inputField label="Correo electrónico" name="email" />
			<petclinic:inputField label="Teléfono pricipal" name="phone" />
			<petclinic:inputField label="Teléfono secundario" name="phone2" />

			<div class="control-group">
				<petclinic:selectFieldMap name="generalPractitioner" label="Médicos disponibles " names="${doctors}" size="1" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Crear Paciente</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>