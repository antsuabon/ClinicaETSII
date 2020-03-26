package org.springframework.clinicaetsii.web.validator;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.validation.BindException;


@ExtendWith(MockitoExtension.class)
public class PatientValidatorTests {

	@Mock
	private PatientService patientService;

	private PatientValidator patientValidator;


	@BeforeEach
	void initPatientFormValidator() {
		this.patientValidator = new PatientValidator(this.patientService);
	}

	Patient patient;

	Doctor doctor;

	@BeforeEach
	void initPatient() {

		this.doctor = new Doctor();
		this.doctor.setId(1);
		this.doctor.setUsername("doctor1");
		this.doctor.setPassword("doctor1");
		this.doctor.setEnabled(true);
		this.doctor.setName("Pablo");
		this.doctor.setSurname("Rodriguez Garrido");
		this.doctor.setDni("45612378P");
		this.doctor.setEmail("pablo@gmail.com");
		this.doctor.setPhone("955668756");
		this.doctor.setCollegiateCode("303092345");

		this.patient = new Patient();
		this.patient.setId(2);
		this.patient.setUsername("patient1");
		this.patient.setPassword("patient1");
		this.patient.setAddress("C/ Ejemplo");
		this.patient.setBirthDate(LocalDate.of(1999, 11, 7));
		this.patient.setDni("11111111A");
		this.patient.setEmail("maria@gmail.com");
		this.patient.setEnabled(true);
		this.patient.setGeneralPractitioner(this.doctor);
		this.patient.setName("Maria");
		this.patient.setSurname("Sanchez Noriega-Cruz");
		this.patient.setNss("12345678900");
		this.patient.setState("Sevilla");
		this.patient.setPhone("666666666");
		this.patient.setPhone2("999999999");

		List<Patient> patients = new ArrayList<Patient>();
		patients.add(this.patient);

	}

	@Test
	void validateAddressNull() {

		this.patient.setAddress(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);


		Assertions.assertThat(errors.hasFieldErrors("address")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);


	}

	@Test
	void validateAddressEmpty() {

		this.patient.setAddress("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("address")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateDniEmpty() {

		this.patient.setDni("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("dni")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateDniNull() {

		this.patient.setDni(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("dni")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@ParameterizedTest
	@ValueSource(strings = {"", "222222222", "AAAAAAAAA", "333D3333R", "D33333333", "333333333E", "3333333F"})
	void validateWhenDni(final String dni) {

		this.patient.setDni(dni);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("dni")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


	@ParameterizedTest
	@ValueSource(strings = {"mariagmail.com", "maria", "@", "@gmail.com", "@gmail .com", "@gmail. com", "maria@"})
	void validateWhenEmail(final String email) {

		this.patient.setEmail(email);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("email")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateEmailNull() {

		this.patient.setEmail(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("email")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateEmailEmpty() {

		this.patient.setEmail("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("email")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateNameNull() {

		this.patient.setName(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("name")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateNameEmpty() {

		this.patient.setName("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("name")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateSurnameNull() {

		this.patient.setSurname(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("surname")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateSurnameEmpty() {

		this.patient.setSurname("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("surname")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);
	}

	@Test
	void validateNSSEmpty() {

		this.patient.setNss("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("nss")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateNSSNull() {

		this.patient.setNss(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("nss")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


	@ParameterizedTest
	@ValueSource(strings = {"", "1234567899", "12345678333333333", "00789", "12789", "53120", "125399995555555559"})
	void validateWhenNSS(final String nss) {

		this.patient.setNss(nss);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("nss")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateStateEmpty() {

		this.patient.setState("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("state")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);
	}

	@Test
	void validateStateNull() {

		this.patient.setState(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("state")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validatePhoneEmpty() {

		this.patient.setPhone("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("phone")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validatePhoneNull() {

		this.patient.setPhone(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("phone")).isTrue();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@ParameterizedTest
	@ValueSource(strings = {"+(3333) 666 666", "(3333) 666 666", "+3333 666 666", "+(3333) 666666", "+(3333) 66666666", "+(3) 666666", "666 666666", "666 66 66 66"})
	void validateWhenPhone(final String phone) {

		this.patient.setPhone(phone);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("phone")).isFalse();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(0);

	}




	@ParameterizedTest
	@ValueSource(strings = {"+(3333) 666 666", "(3333) 666 666", "+3333 666 666", "+(3333) 666666", "+(3333) 66666666", "+(3) 666666", "666 666666", "666 66 66 66"})
	void validateWhenPhone2(final String phone) {

		this.patient.setPhone2(phone);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		Assertions.assertThat(errors.hasFieldErrors("phone2")).isFalse();
		Assertions.assertThat(errors.getFieldErrorCount()).isEqualTo(0);

	}


}