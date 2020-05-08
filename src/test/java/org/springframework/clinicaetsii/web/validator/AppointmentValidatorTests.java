
package org.springframework.clinicaetsii.web.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful when
 *         upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
public class AppointmentValidatorTests {

	private Appointment appointment;
	private Patient patient1;
	private Doctor doctor1;

	private AppointmentValidator createValidator() {
		AppointmentValidator validator = new AppointmentValidator();
		return validator;
	}

	@BeforeEach
	void initAppointment() {

		this.doctor1 = new Doctor();
		this.doctor1.setId(1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Antonio");
		this.doctor1.setSurname("Suarez Bono");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("antonio@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303024345");

		this.patient1 = new Patient();
		this.patient1.setId(1);
		this.patient1.setAddress("Calle Oscar Arias");
		this.patient1.setBirthDate(LocalDate.now());
		this.patient1.setDni("41235678L");
		this.patient1.setEmail("pedro@gmail.com");
		this.patient1.setEnabled(true);
		this.patient1.setGeneralPractitioner(this.doctor1);
		this.patient1.setName("Pedro");
		this.patient1.setNss("12345778S");
		this.patient1.setPassword("patient1");
		this.patient1.setPhone("123456789");
		this.patient1.setState("España");
		this.patient1.setSurname("Roldán");
		this.patient1.setUsername("patient1");


		this.appointment = new Appointment();
		LocalDateTime date = LocalDateTime.of(2020, 3, 28, 9, 0);
		this.appointment.setStartTime(date);
		this.appointment.setEndTime(date.plusMinutes(7));
		this.appointment.setPatient(this.patient1);

	}

	@Test
	void shouldNotValidateStartTimeAppointment() {

		AppointmentValidator validator = createValidator();

		this.appointment.setStartTime(null);

		Errors errors = new BeanPropertyBindingResult(this.appointment, "appointment");
		validator.validate(this.appointment, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);

		Assertions.assertThat(errors.getFieldError().getField()).isEqualTo("startTime");

		Assertions.assertThat(errors.getFieldError().getDefaultMessage())
				.isEqualTo("La fecha de inicio de la cita no puede ser nula");

	}

	@Test
	void shouldNotValidateEndTimeAppointment() {

		AppointmentValidator validator = createValidator();

		this.appointment.setEndTime(null);

		Errors errors = new BeanPropertyBindingResult(this.appointment, "appointment");
		validator.validate(this.appointment, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);

		Assertions.assertThat(errors.getFieldError().getField()).isEqualTo("endTime");

		Assertions.assertThat(errors.getFieldError().getDefaultMessage())
				.isEqualTo("La fecha de fin de la cita no puede ser nula");

	}

	@Test
	void shouldNotValidateOverEndTimeAppointment() {

		AppointmentValidator validator = createValidator();

		this.appointment.setEndTime(this.appointment.getEndTime().plusMinutes(1));

		Errors errors = new BeanPropertyBindingResult(this.appointment, "appointment");
		validator.validate(this.appointment, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);

		Assertions.assertThat(errors.getFieldError().getField()).isEqualTo("endTime");

		Assertions.assertThat(errors.getFieldError().getDefaultMessage()).isEqualTo(
				"La diferecia entre en comienzo y fin de la cita debe de ser de 7 minutos");

	}

}
