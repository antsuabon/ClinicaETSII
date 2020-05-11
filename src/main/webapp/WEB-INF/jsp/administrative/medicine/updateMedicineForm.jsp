<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="medicine">

    
   <form:form modelAttribute="medicine" id="update-medicine" >                
            <input type="hidden" name="id" value="${medicine.id}"/>
	<div class="form-group has-feedback">
                    <petclinic:inputField label="Nombre Genérico" name="genericalName"/>
             <petclinic:inputField label="Nombre Comercial" name="commercialName" />
                      <petclinic:inputField label="Cantidad" name="quantity"/>
                       <petclinic:inputField label="Indicaciones" name="indications"/>
                        <petclinic:inputField label="Contraindicaciones" name="contraindications"/>
              
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
   
                            <button class="btn btn-default" type="submit">Actualizar Medicamento</button>
       </div>          
               
                </div>
            </div>
     </form:form>
 
</petclinic:layout>