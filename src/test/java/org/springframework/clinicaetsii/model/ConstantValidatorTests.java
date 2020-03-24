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

public class ConstantValidatorTests {

	Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private ConstantType constantType1;
	private ConstantType constantType6;
	private Constant constant1;
	private Constant constant2;

	@BeforeEach
	void initConstant() {

		this.constantType1 = new ConstantType();
		this.constantType1.setId(1);
		this.constantType1.setName("Peso");

		this.constantType6 = new ConstantType();
		this.constantType6.setId(6);
		this.constantType6.setName("FrecCard");

		this.constant1 = new Constant();
		this.constant1.setConstantType(this.constantType1);
		this.constant1.setValue(52f);

		this.constant2 = new Constant();
		this.constant2.setConstantType(this.constantType6);
		this.constant2.setValue(92f);

	}

	@Test
	void shouldValidateWhenConstantTypeNull() {

		this.constant1.setConstantType(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Constant>> constraintViolations = validator.validate(this.constant1);

		Assertions.assertThat(constraintViolations).hasSize(0);
	}

	@ParameterizedTest
	@ValueSource(strings = {"Pes", "Pulsioximetría", "PesoPesoPesoPesoPeso", "PesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPeso"})
	void shouldValidateWhenConstantTypeName(final String constantTypeName) {

		this.constantType1.setName(constantTypeName);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Constant>> constraintViolations = validator.validate(this.constant1);

		Assertions.assertThat(constraintViolations).hasSize(0);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "Pe", "PesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoPesoP"})
	void shouldNotValidateWhenConstantTypeInvalidName(final String constantTypeName) {

		this.constantType1.setName(constantTypeName);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<Constant>> constraintViolations = validator.validate(this.constant1);

		Assertions.assertThat(constraintViolations).hasSize(1);
	}


}
