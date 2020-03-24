package org.springframework.clinicaetsii.model;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class DoctorValidatorTests {

	Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Doctor doctor;

	@BeforeEach
	void initDoctor() {
		Doctor doctor = new Doctor();
		doctor.setId(1);
		doctor.setUsername("doctor1");
		doctor.setEnabled(true);
		doctor.setName("Antonio");
		doctor.setSurname("Suarez Bono");
		doctor.setDni("45612378P");
		doctor.setEmail("antonio@gmail.com");
		doctor.setPhone("955668756");
		doctor.setCollegiateCode("303024345");

		this.doctor = doctor;
	}

	@Test
	void shouldNotValidateWhenUsernameEmpty() {

		this.doctor.setUsername("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenUsernameNull() {

		this.doctor.setUsername("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenNameEmpty() {

		this.doctor.setName("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenNameNull() {

		this.doctor.setName("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenSurnameEmpty() {

		this.doctor.setSurname("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenSurnameNull() {

		this.doctor.setSurname("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenNameOrSurnameEmpty() {

		this.doctor.setName("");
		this.doctor.setSurname("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(2);
		Assertions.assertThat(this.doctor.getFullName()).isEqualTo(", ");
	}

	@Test
	void shouldNotValidateWhenNameOrSurnameNull() {

		this.doctor.setName(null);
		this.doctor.setSurname(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(2);
		Assertions.assertThat(this.doctor.getName()).isNull();
	}

	@Test
	void shouldNotValidateWhenDNIEmpty() {

		this.doctor.setDni("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);

	}

	@Test
	void shouldNotValidateWhenDNINull() {

		this.doctor.setDni(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenEmailEmpty() {

		this.doctor.setEmail("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenEmailNull() {

		this.doctor.setEmail(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"antonio@gmail.com", "antonio@gmail"})
	void shouldValidateWhenEmail(final String email) {

		this.doctor.setEmail(email);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).isEmpty();
	}

	@ParameterizedTest
	@ValueSource(strings = {"antoniogmail.com", "antonio", "@", "@gmail.com", "@gmail .com", "@gmail. com", "antonio@", "antonio@ gmail.com", "antonio @gmail.com"})
	void shouldNotValidateWhenEmail(final String email) {

		this.doctor.setEmail(email);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenPhoneEmpty() {

		this.doctor.setPhone("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenPhoneNull() {

		this.doctor.setPhone(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenCollegiateCodeEmpty() {

		this.doctor.setCollegiateCode("");

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}

	@Test
	void shouldNotValidateWhenCollegiateCodeNull() {

		this.doctor.setCollegiateCode(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Doctor>> constraintViolations = validator.validate(this.doctor);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}



}
