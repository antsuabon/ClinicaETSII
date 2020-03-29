package org.springframework.clinicaetsii.web.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.User;
import org.springframework.clinicaetsii.model.form.DoctorForm;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.validation.BindException;

@ExtendWith(MockitoExtension.class)
public class DoctorFormValidatorTests {

	@Mock
	private DoctorService doctorService;

	@Mock
	private UserService userService;

	private DoctorFormValidator doctorFormValidator;

	@BeforeEach
	void initDoctorFormValidator() {
		this.doctorFormValidator = new DoctorFormValidator(this.doctorService, this.userService);
	}


	DoctorForm doctorForm1;
	Doctor doctor2;
	User user2;

	@BeforeEach
	void initDoctors() {
		Doctor doctor1 = new Doctor();
		doctor1.setId(1);
		doctor1.setUsername("doctor1");
		doctor1.setEnabled(true);
		doctor1.setName("Antonio");
		doctor1.setSurname("Suarez Bono");
		doctor1.setDni("45612378P");
		doctor1.setEmail("antonio@gmail.com");
		doctor1.setPhone("955668756");
		doctor1.setCollegiateCode("303024345");

		this.doctor2 = new Doctor();
		this.doctor2.setId(2);
		this.doctor2.setUsername("doctor2");
		this.doctor2.setPassword("doctor2");
		this.doctor2.setEnabled(true);
		this.doctor2.setName("Alejandro");
		this.doctor2.setSurname("Sánchez López");
		this.doctor2.setDni("45612555S");
		this.doctor2.setEmail("ale@gmail.com");
		this.doctor2.setPhone("955668777");
		this.doctor2.setCollegiateCode("303051345");

		Doctor doctor3 = new Doctor();
		doctor3.setId(1);
		doctor3.setUsername("doctor1");
		doctor3.setEnabled(true);
		doctor3.setName("Antonio");
		doctor3.setSurname("Suarez Bono");
		doctor3.setDni("45612378P");
		doctor3.setEmail("antonio@gmail.com");
		doctor3.setPhone("955668756");
		doctor3.setCollegiateCode("303024345");

		this.doctorForm1 = new DoctorForm();
		this.doctorForm1.setDoctor(doctor3);

		this.user2 = new User();
		this.user2.setId(2);
		this.user2.setUsername("doctor2");
		this.user2.setPassword("doctor2");
		this.user2.setEnabled(true);
		this.user2.setName("Alejandro");
		this.user2.setSurname("Sánchez López");
		this.user2.setDni("45612555S");
		this.user2.setEmail("ale@gmail.com");
		this.user2.setPhone("955668777");

		when(this.doctorService.findCurrentDoctor()).thenReturn(doctor1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"doctor1", "doctorNew"})
	void shouldValidateUsername(final String username) {

		if (!username.equals("doctor1")) {
			when(this.userService.findUserByUsername(Mockito.anyString())).thenReturn(null);
		}

		this.doctorForm1.getDoctor().setUsername(username);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasErrors()).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "doctor2"})
	void shouldNotValidateUsername(final String username) {

		if (!username.isEmpty()) {
			when(this.userService.findUserByUsername(Mockito.anyString())).thenReturn(this.user2);
		}

		this.doctorForm1.getDoctor().setUsername(username);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.username")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateNullUsername() {

		// when(this.userService.findUserByUsername(null)).thenReturn(null);

		this.doctorForm1.getDoctor().setUsername(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.username")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWithoutPassword() {

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasErrors()).isFalse();
	}

	@Test
	void shouldValidateWithNameNull() {

		this.doctorForm1.getDoctor().setName(null);
		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.name")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWithNameBlank() {

		this.doctorForm1.getDoctor().setName("");
		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.name")).isTrue();
		assertThat(errors.getFieldErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWithSurnameNull() {

		this.doctorForm1.getDoctor().setSurname(null);
		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.surname")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWithSurnameBlank() {

		this.doctorForm1.getDoctor().setSurname("");
		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.surname")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}


	@ParameterizedTest
	@ValueSource(strings = {"", "aa2dBe", "dsdsddsR3", "aa2dBedddddddefrgth6"})
	void shouldValidateWithPassword(final String newPassword) {

		this.doctorForm1.setNewPassword(newPassword);
		this.doctorForm1.setRepeatPassword(newPassword);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("newPassword")).isFalse();
		assertThat(errors.hasErrors()).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"aD3da", "111111", "AAAAAA", "aaaaaa", "sssssssssssssss3R56ddd"})
	void shouldNotValidateWithPassword(final String newPassword) {

		this.doctorForm1.setNewPassword(newPassword);
		this.doctorForm1.setRepeatPassword(newPassword);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("newPassword")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWithRepeatPasswordNull() {

		this.doctorForm1.setNewPassword("aa2dBe");
		this.doctorForm1.setRepeatPassword(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("repeatPassword")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWithDifferentRepeatPassword() {

		this.doctorForm1.setNewPassword("aa2dBe");
		this.doctorForm1.setRepeatPassword("aA2dBe");

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("repeatPassword")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWithDniNull() {

		this.doctorForm1.getDoctor().setDni(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.dni")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "222222222", "AAAAAAAAA", "333D3333R", "D33333333", "333333333E",
			"3333333F"})
	void shouldNotValidateWithDni(final String dni) {

		this.doctorForm1.getDoctor().setDni(dni);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.dni")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"11111111D", "12345678A", "12345432Z"})
	void shouldValidateWithDni(final String dni) {

		this.doctorForm1.getDoctor().setDni(dni);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.dni")).isFalse();
		assertThat(errors.hasErrors()).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"antonio@gmail.com", "antonio@gmail"})
	void shouldValidateWithEmail(final String email) {

		this.doctorForm1.getDoctor().setEmail(email);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.email")).isFalse();
		assertThat(errors.hasErrors()).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "antoniogmail.com", "antonio", "@", "@gmail.com", "@gmail .com",
			"@gmail. com", "antonio@"})
	void shouldNotValidateWithEmail(final String email) {

		this.doctorForm1.getDoctor().setEmail(email);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.email")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWithEmailNull() {

		this.doctorForm1.getDoctor().setEmail(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.email")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);

	}

	@Test
	void shouldNotValidateWithCollegiateCodeNull() {

		this.doctorForm1.getDoctor().setCollegiateCode(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.collegiateCode")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);

	}

	@ParameterizedTest
	@ValueSource(strings = {"221400543", "011200000", "010100000", "521300000"})
	void shouldValidateWithCollegiateCode(final String collegiateCode) {

		this.doctorForm1.getDoctor().setCollegiateCode(collegiateCode);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.collegiateCode")).isFalse();
		assertThat(errors.hasErrors()).isFalse();

	}

	@ParameterizedTest
	@ValueSource(strings = {"", "1234567899", "12345678", "003456789", "120056789", "531200000",
			"125399999"})
	void shouldNotValidateWithCollegiateCode(final String collegiateCode) {

		this.doctorForm1.getDoctor().setCollegiateCode(collegiateCode);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.collegiateCode")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);

	}

	@ParameterizedTest
	@ValueSource(strings = {"+(3333) 666 666", "(3333) 666 666", "+3333 666 666", "+(3333) 666666",
			"+(3333) 66666666", "+(3) 666666", "666 666666", "666 66 66 66"})
	void shouldValidateWithPhone(final String phone) {

		this.doctorForm1.getDoctor().setPhone(phone);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.phone")).isFalse();
		assertThat(errors.hasErrors()).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "+ (3333) 666 666", "+ 3333 666 666"})
	void shouldNotValidateWithPhone(final String phone) {

		this.doctorForm1.getDoctor().setPhone(phone);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.phone")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWithPhoneNull() {

		this.doctorForm1.getDoctor().setPhone(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		assertThat(errors.hasFieldErrors("doctor.phone")).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(1);

	}

}
