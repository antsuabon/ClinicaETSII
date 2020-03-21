
package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.validator.AppointmentValidator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 *         when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AppointmentValidatorTests {

	private AppointmentValidator createValidator() {
		AppointmentValidator validator = new AppointmentValidator();
		return validator;
	}


	@Autowired
	protected PatientService patientService;


	@Test
	void shouldValidateStartTimeAppointment() {

		Appointment appointment = new Appointment();
		LocalDateTime date = LocalDateTime.now();
		Patient patient = this.patientService.findPatient(4);
		appointment.setEndTime(date.plusMinutes(7));
		appointment.setPatient(patient);
		Object object = appointment;
		AppointmentValidator validator = this.createValidator();

		Errors errors = new BeanPropertyBindingResult(object, "appointment");
		validator.validate(object, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);

		Assertions.assertThat(errors.getFieldError().getField()).isEqualTo("startTime");

		Assertions.assertThat(errors.getFieldError().getDefaultMessage()).isEqualTo("La fecha de inicio de la cita no puede ser nula");

	}

	@Test
	void shouldValidateEndTimeAppointment() {

		Appointment appointment = new Appointment();
		LocalDateTime date = LocalDateTime.now();
		Patient patient = this.patientService.findPatient(4);
		appointment.setStartTime(date);
		appointment.setPatient(patient);
		Object object = appointment;
		AppointmentValidator validator = this.createValidator();

		Errors errors = new BeanPropertyBindingResult(object, "appointment");
		validator.validate(object, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);

		Assertions.assertThat(errors.getFieldError().getField()).isEqualTo("endTime");

		Assertions.assertThat(errors.getFieldError().getDefaultMessage()).isEqualTo("La fecha de fin de la cita no puede ser nula");

	}

	@Test
	void shouldValidateOverEndTimeAppointment() {

		Appointment appointment = new Appointment();
		LocalDateTime date = LocalDateTime.now();
		Patient patient = this.patientService.findPatient(4);
		appointment.setStartTime(date);
		appointment.setPatient(patient);
		appointment.setEndTime(date.minusMinutes(7));
		Object object = appointment;
		AppointmentValidator validator = this.createValidator();

		Errors errors = new BeanPropertyBindingResult(object, "appointment");
		validator.validate(object, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);

		Assertions.assertThat(errors.getFieldError().getField()).isEqualTo("endTime");

		Assertions.assertThat(errors.getFieldError().getDefaultMessage()).isEqualTo("La diferecia entre en comienzo y fin de la cita debe de ser de 7 minutos");

	}

}
