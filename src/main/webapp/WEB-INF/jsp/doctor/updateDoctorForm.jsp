<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="doctor profile">
	<h2>M�dico</h2>
	<form:form modelAttribute="doctorForm" class="form-horizontal" id="update-doctor-form">
		<div class="form-group has-feedback">

			<br />
			<h3>Datos Personales</h3>

			<petclinic:inputField label="Nombre" name="doctor.name" />
			<petclinic:inputField label="Apellidos" name="doctor.surname" />
			<petclinic:inputField label="DNI" name="doctor.dni" />
			<petclinic:inputField label="Correo electr�nico" name="doctor.email" />
			<petclinic:inputField label="Tel�fono" name="doctor.phone" />

			<br />
			<h3>Datos de Usuario</h3>

			<petclinic:inputField label="Nombre de usuario" name="doctor.username" />
			<petclinic:inputField label="Nueva contrase�a" name="newPassword" type="password" />
			<petclinic:inputField label="Nueva contrase�a" name="repeatPassword" type="password" />
			
			<br />
			<h3>Datos M�dicos</h3>
			<petclinic:inputField label="N�mero de colegiado" name="doctor.collegiateCode" />

			<br />
			<div class="control-group">
				<petclinic:selectFieldMap name="doctor.services" label="Servicios " names="${allServices}" size="10" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Actualizar perfil</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>