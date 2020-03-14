<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="consultations">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#startTime").datetimepicker();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${consultation['new']}">Nueva </c:if> Consulta
        </h2>
        <form:form modelAttribute="consultation"
                   class="form-horizontal">
                   
            <input type="hidden" name="id" value="${consultation.id}"/>
            <input type="hidden" name="appointmentId" value="${consultation.appointment.id}"/>
            
            <div class="form-group has-feedback">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Owner</label>
                    <div class="col-sm-10">
                        <c:out value="${consultation.appointment.patient.fullName}"/>
                    </div>
                </div>
                
                <div>
                	<petclinic:inputField label="Fecha de inicio" name="startTime"/>
                	<petclinic:inputField label="Fecha de fin" name="endTime"/>
                </div>

				<petclinic:inputField label="Anamnesis" name="anamnesis"/>
                <petclinic:inputField label="Observaciones" name="remarks"/>
                
                <div class="control-group">
                    <petclinic:selectFieldMap name="dischargeType" label="Alta " names="${dischargeTypes}" size="6"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${consultation['new']}">
                            <button class="btn btn-default" type="submit">Añadir Consulta</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Actualizar Consulta</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
        <c:if test="${!consultation['new']}">
        </c:if>
    </jsp:body>
</petclinic:layout>