
package org.springframework.clinicaetsii.web.validator;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AppointmentValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(final Object target, final Errors errors) {

		Appointment appointment = (Appointment) target;

		if (appointment.getStartTime() == null) {
			errors.rejectValue("startTime", "no_startTime", "La fecha de inicio de la cita no puede ser nula");
		} else if (appointment.getEndTime() == null) {
			errors.rejectValue("endTime", "no_endTime", "La fecha de fin de la cita no puede ser nula");
		} else if (!appointment.getStartTime().equals(appointment.getEndTime().minusMinutes(7))) {
			errors.rejectValue("endTime", "over_minutes", "La diferecia entre en comienzo y fin de la cita debe de ser de 7 minutos");
		}

	}

}
