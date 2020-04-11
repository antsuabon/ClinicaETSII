
package org.springframework.clinicaetsii.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConsultationValidator implements Validator {

	private AppointmentService	appointmentService;
	private ConsultationService	consultationService;
	private Integer				appointmentId;
	private Integer				consultationId;


	@Autowired
	public ConsultationValidator(final AppointmentService appointmentService, final ConsultationService	consultationService, final Integer appointmentId, final Integer consultationId) {
		this.appointmentService = appointmentService;
		this.consultationService = consultationService;
		this.appointmentId = appointmentId;
		this.consultationId = consultationId;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return Consultation.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		Appointment appointment = this.appointmentService.findAppointmentById(this.appointmentId);

		Consultation cosultation = new Consultation();
		if (this.consultationId != null) {
			cosultation = this.consultationService.findConsultationById(this.consultationId);
		}

		Consultation consultationToUpdate = (Consultation) target;

		if (consultationToUpdate.getStartTime() == null) {
			errors.rejectValue("startTime", "requiredStartTime", "La fecha de inicio es obligatoria");
		} else if (appointment.getStartTime().isAfter(consultationToUpdate.getStartTime())) {
			errors.rejectValue("startTime", "invalidStartTime", "La fecha de inicio de la consulta debe ser igual o posterior al de la cita");
		}

		if (consultationToUpdate.getDischargeType() != null && !cosultation.isNew() && (cosultation.getExaminations() == null || cosultation.getExaminations().isEmpty())) {
			errors.rejectValue("dischargeType", "emptyExplorations", "No es posible dar de alta una consulta sin exploraciones");
		}
	}

}
