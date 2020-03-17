<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="doctor consultations">
    <jsp:body>
        <h2>
            <c:if test="${constant['new']}">Nueva </c:if> Constante
        </h2>
        <form:form modelAttribute="constant"
                   class="form-horizontal">
                   
            <input type="hidden" name="id" value="${constant.id}"/>
            <input type="hidden" name="consultationId" value="${consultationId}"/>
            
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label"></label>
                    <div class="col-sm-10">
                    </div>
                </div>
                <div class="control-group">
                    <petclinic:selectFieldMap name="constantType" label="Tipo de constante " names="${constantTypes}" size="12"/>
                </div>
                
                <div>
                	<petclinic:inputField label="Valor" name="value"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${constant['new']}">
                            <button class="btn btn-default" type="submit">Añadir Constante</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar Constante</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!constant['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>