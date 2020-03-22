package org.springframework.clinicaetsii.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.clinicaetsii.model.form.DoctorForm;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.web.validator.DoctorFormValidator;
import org.springframework.validation.BindException;

@ExtendWith(MockitoExtension.class)
public class DoctorFormValidatorTests {

	@Mock
	private DoctorService doctorService;

	private DoctorFormValidator doctorFormValidator;

	@BeforeEach
	void initDoctorFormValidator() {
		this.doctorFormValidator = new DoctorFormValidator(this.doctorService);
	}


	DoctorForm doctorForm1;


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

		Doctor doctor2 = new Doctor();
		doctor2.setId(2);
		doctor2.setUsername("doctor2");
		doctor2.setPassword("doctor2");
		doctor2.setEnabled(true);
		doctor2.setName("Alejandro");
		doctor2.setSurname("Sánchez López");
		doctor2.setDni("45612555S");
		doctor2.setEmail("ale@gmail.com");
		doctor2.setPhone("955668777");
		doctor2.setCollegiateCode("303051345");

		this.doctorForm1 = new DoctorForm();
		this.doctorForm1.setDoctor(doctor1);

		Mockito.when(this.doctorService.findCurrentDoctor()).thenReturn(doctor1);
	}

	@Test
	void shouldValidateWithoutPassword() {

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		Assertions.assertThat(errors.hasErrors()).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "aa2dBe", "dsdsddsR3", "aa2dBedddddddefrgth6"})
	void shouldValidateWithPassword(final String newPassword) {

		this.doctorForm1.setNewPassword(newPassword);
		this.doctorForm1.setRepeatPassword(newPassword);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		Assertions.assertThat(errors.hasErrors()).isFalse();
		Assertions.assertThat(errors.hasFieldErrors("newPassword")).isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = {"aD3da", "111111", "AAAAAA", "aaaaaa", "sssssssssssssss3R56ddd"})
	void shouldNotValidateWithPassword(final String newPassword) {

		this.doctorForm1.setNewPassword(newPassword);
		this.doctorForm1.setRepeatPassword(newPassword);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(errors.hasFieldErrors("newPassword")).isTrue();
	}

	@Test
	void shouldNotValidateWithRepeatPasswordNull() {

		this.doctorForm1.setNewPassword("aa2dBe");
		this.doctorForm1.setRepeatPassword(null);

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(errors.hasFieldErrors("repeatPassword")).isTrue();
	}

	@Test
	void shouldNotValidateWithDifferentRepeatPassword() {

		this.doctorForm1.setNewPassword("aa2dBe");
		this.doctorForm1.setRepeatPassword("aA2dBe");

		BindException errors = new BindException(this.doctorForm1, "doctorForm");
		this.doctorFormValidator.validate(this.doctorForm1, errors);

		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
		Assertions.assertThat(errors.hasFieldErrors("repeatPassword")).isTrue();
	}

}
