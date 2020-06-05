<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="patient">

	<jsp:attribute name="customScript">
        <script>
            $(function () {$('input[name ="patient.birthDate"]').datepicker({dateFormat: 'dd/mm/yy'});});
        </script>
	</jsp:attribute>
    
	<jsp:body>
    <h2> Patient </h2>
    <form:form modelAttribute="patientForm" class="form-horizontal" id="add-patient-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="patient.name"/>
            <petclinic:inputField label="Last Name" name="patient.surname"/>
            <petclinic:inputField label="Birth Date" name="patient.birthDate"/>
            <petclinic:inputField label="Address" name="patient.address"/>
            <petclinic:inputField label="State" name="patient.state"/>
            <petclinic:inputField label="NSS" name="patient.nss"/>
            <petclinic:inputField label="DNI" name="patient.dni"/>
            <petclinic:inputField label="Email" name="patient.email"/>
            <petclinic:inputField label="Telephone" name="patient.phone"/>
            <petclinic:inputField label="Telephone 2" name="patient.phone2"/>
            
            <div class="control-group">
				<petclinic:selectFieldMap name="patient.generalPractitioner" label="Médicos disponibles " names="${doctors}" size="1" />
			</div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Actualizar Paciente</button>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
