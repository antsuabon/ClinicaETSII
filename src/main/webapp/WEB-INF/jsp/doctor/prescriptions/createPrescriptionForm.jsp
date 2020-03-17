<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="prescriptions">
    <h2>
        Nueva prescripci�n
    </h2>
    <form:form modelAttribute="prescription" class="form-horizontal" id="add-prescription-form">
       
       
        <div class="form-group has-feedback">
             <petclinic:inputField label="Dosis" name="dosage"/>
             <petclinic:inputField label="D�as" name="days"/>
             <petclinic:inputField label="Aviso al farmac�utico" name="pharmaceuticalWarning"/>
             <petclinic:inputField label="Aviso al paciente" name="patientWarning"/>
             <petclinic:selectFieldMap label="Medicamentos disponibles" name="medicine" size="1" names="${medicines}"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                   <button class="btn btn-default" type="submit">A�adir prescripci�n</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>