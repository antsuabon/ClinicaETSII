  
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="patient">

    <h2>Nuevo Paciente</h2>
    <form:form modelAttribute="patient" class="form-horizontal" id="add-patient-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="name"/>
            <petclinic:inputField label="Apellidos" name="surname"/>
            <petclinic:inputField label="NSS" name="nss"/>
            <petclinic:inputField label="DNI" name="dni"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Teléfono" name="phone"/>
            <petclinic:inputField label="Teléfono 2" name="phone2"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            <button class="btn btn-default" type="submit">Añadir Paciente</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>