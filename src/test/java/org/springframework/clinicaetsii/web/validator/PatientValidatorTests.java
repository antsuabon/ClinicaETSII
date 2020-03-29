package org.springframework.clinicaetsii.web.validator;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.validation.BindException;


@ExtendWith(MockitoExtension.class)
public class PatientValidatorTests {

	@Mock
	private PatientService patientService;

	@Mock
	private UserService userService;

	private PatientValidator patientValidator;


	@BeforeEach
	void initPatientFormValidator() {
		this.patientValidator = new PatientValidator(this.patientService, this.userService);
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


		assertThat(errors.hasFieldErrors("address")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);


	}

	@Test
	void validateAddressEmpty() {

		this.patient.setAddress("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("address")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateDniEmpty() {

		this.patient.setDni("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("dni")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateDniNull() {

		this.patient.setDni(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("dni")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@ParameterizedTest
	@ValueSource(strings = {"", "222222222", "AAAAAAAAA", "333D3333R", "D33333333", "333333333E",
			"3333333F"})
	void validateWhenDni(final String dni) {

		this.patient.setDni(dni);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("dni")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


	@ParameterizedTest
	@ValueSource(strings = {"mariagmail.com", "maria", "@", "@gmail.com", "@gmail .com",
			"@gmail. com", "maria@"})
	void validateWhenEmail(final String email) {

		this.patient.setEmail(email);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("email")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateEmailNull() {

		this.patient.setEmail(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("email")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateEmailEmpty() {

		this.patient.setEmail("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("email")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateNameNull() {

		this.patient.setName(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("name")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateNameEmpty() {

		this.patient.setName("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("name")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateSurnameNull() {

		this.patient.setSurname(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("surname")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateSurnameEmpty() {

		this.patient.setSurname("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("surname")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);
	}

	@Test
	void validateNSSEmpty() {

		this.patient.setNss("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("nss")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateNSSNull() {

		this.patient.setNss(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("nss")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


	@ParameterizedTest
	@ValueSource(strings = {"", "1234567899", "12345678333333333", "00789", "12789", "53120",
			"125399995555555559"})
	void validateWhenNSS(final String nss) {

		this.patient.setNss(nss);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("nss")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateStateEmpty() {

		this.patient.setState("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("state")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);
	}

	@Test
	void validateStateNull() {

		this.patient.setState(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("state")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validatePhoneEmpty() {

		this.patient.setPhone("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validatePhoneNull() {

		this.patient.setPhone(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@ParameterizedTest
	@ValueSource(strings = {"+(3333) 666 666", "(3333) 666 666", "+3333 666 666", "+(3333) 666666",
			"+(3333) 66666666", "+(3) 666666", "666 666666", "666 66 66 66"})
	void validateWhenPhone(final String phone) {

		this.patient.setPhone(phone);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone")).isFalse();
		assertThat(errors.getFieldErrorCount()).isEqualTo(0);

	}


	@ParameterizedTest
	@ValueSource(strings = {"", "+ (3333) 666 666", "+ 3333 666 666"})
	void notValidateWhenPhone(final String phone) {

		this.patient.setPhone(phone);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


	@ParameterizedTest
	@ValueSource(strings = {"", "+(3333) 666 666", "(3333) 666 666", "+3333 666 666",
			"+(3333) 666666", "+(3333) 66666666", "+(3) 666666", "666 666666", "666 66 66 66"})
	void validateWhenPhone2(final String phone) {

		this.patient.setPhone2(phone);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone2")).isFalse();
		assertThat(errors.getFieldErrorCount()).isEqualTo(0);

	}

	@Test
	void validateWhenPhone2IsNull() {

		this.patient.setPhone2(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone2")).isFalse();
		assertThat(errors.getFieldErrorCount()).isEqualTo(0);

	}

	@ParameterizedTest
	@ValueSource(strings = {"+ (3333) 666 666", "+ 3333 666 666"})
	void notValidateWhenPhone2(final String phone) {

		this.patient.setPhone2(phone);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("phone2")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


	@Test
	void notValidateBirthDate() {

		this.patient.setBirthDate(LocalDate.now().plusDays(1));

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("birthDate")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateUsernameNull() {

		this.patient.setUsername(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("username")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateUsernameBlank() {

		this.patient.setUsername("");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("username")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateUsernameRepeated() {

		given(this.userService.findUserByUsername("doctor1")).willReturn(this.doctor);
		this.patient.setUsername("doctor1");

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("username")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}

	@Test
	void validateGeneralPractitionerNull() {

		this.patient.setGeneralPractitioner(null);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("generalPractitioner")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);
	}

	@Test
	void validateGeneralPractitionerTooManyPatients() {

		Patient p1 = new Patient();
		p1.setAddress("asdf");
		p1.setBirthDate(LocalDate.of(1999, 12, 12));
		p1.setDni("wtgwrtgwrtyh");
		p1.setEmail("algo@gmail.com");
		p1.setGeneralPractitioner(this.doctor);
		p1.setName("jose");
		p1.setNss("ertgwtgw");
		p1.setPassword("password");
		p1.setUsername("p1");
		p1.setPhone("897786678");
		p1.setSurname("asdfaf");
		p1.setPhone2("231423");
		p1.setState("aqwrefgr");
		p1.setId(1);

		Patient p2 = new Patient();
		p2.setAddress("asdf");
		p2.setBirthDate(LocalDate.of(1999, 12, 12));
		p2.setDni("fsgsfgbr");
		p2.setEmail("algo@gmail.com");
		p2.setGeneralPractitioner(this.doctor);
		p2.setName("jose");
		p2.setNss("yujrutyjr");
		p2.setPassword("password");
		p2.setUsername("p2");
		p2.setPhone("897786678");
		p2.setSurname("asdfaf");
		p2.setPhone2("231423");
		p2.setState("aqwrefgr");
		p2.setId(200);

		Patient p3 = new Patient();
		p3.setAddress("asdf");
		p3.setBirthDate(LocalDate.of(1999, 12, 12));
		p3.setDni("etrhwg");
		p3.setEmail("algo@gmail.com");
		p3.setGeneralPractitioner(this.doctor);
		p3.setName("jose");
		p3.setNss("sdfgrtghw");
		p3.setPassword("password");
		p3.setUsername("p3");
		p3.setPhone("897786678");
		p3.setSurname("asdfaf");
		p3.setPhone2("231423");
		p3.setState("aqwrefgr");
		p3.setId(300);

		Patient p4 = new Patient();
		p4.setAddress("asdf");
		p4.setBirthDate(LocalDate.of(1999, 12, 12));
		p4.setDni(" utehretg");
		p4.setEmail("algo@gmail.com");
		p4.setGeneralPractitioner(this.doctor);
		p4.setName("jose");
		p4.setNss("efgwertg");
		p4.setPassword("password");
		p4.setUsername("p4");
		p4.setPhone("897786678");
		p4.setSurname("asdfaf");
		p4.setPhone2("231423");
		p4.setState("aqwrefgr");
		p4.setId(4);

		Patient p5 = new Patient();
		p5.setAddress("asdf");
		p5.setBirthDate(LocalDate.of(1999, 12, 12));
		p5.setDni("wtrhrtyhwtg");
		p5.setEmail("algo@gmail.com");
		p5.setGeneralPractitioner(this.doctor);
		p5.setName("jose");
		p5.setNss("sdfgwtgw");
		p5.setPassword("password");
		p5.setUsername("p5");
		p5.setPhone("897786678");
		p5.setSurname("asdfaf");
		p5.setPhone2("231423");
		p5.setState("aqwrefgr");
		p5.setId(5);

		List<Patient> current = new ArrayList<>();
		current.add(p1);
		current.add(p3);
		current.add(p3);
		current.add(p4);
		current.add(p5);

		given(this.patientService.findAllPatientsFromDoctor(doctor.getId())).willReturn(current);

		this.patient.setGeneralPractitioner(this.doctor);

		BindException errors = new BindException(this.patient, "patient");
		this.patientValidator.validate(this.patient, errors);

		assertThat(errors.hasFieldErrors("generalPractitioner")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);

	}


}
