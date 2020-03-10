<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="patients">
    <h2>
        Patient
    </h2>
    
     
    <form:form modelAttribute="patient" class="form-horizontal" id="add-patient-form">
        <div class="form-group has-feedback">
            <petclinic:selectField label="Doctors" name="generalPractitioner" size="1" names="${doctors}"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Update Patient</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
