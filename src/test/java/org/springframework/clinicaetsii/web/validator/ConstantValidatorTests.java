package org.springframework.clinicaetsii.web.validator;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.validation.BindException;

@ExtendWith(MockitoExtension.class)
public class ConstantValidatorTests {

	private ConstantValidator constantValidator;

	@Mock
	private ConsultationService consultationService;

	private Consultation consultation1;

	private ConstantType constantType1;
	private ConstantType constantType6;
	private Constant constant1;
	private Constant constant2;
	private Constant constant1ToUpdate;

	@BeforeEach
	private void initConstants() {
		this.constantType1 = new ConstantType();
		this.constantType1.setId(1);
		this.constantType1.setName("Peso");

		this.constantType6 = new ConstantType();
		this.constantType6.setId(6);
		this.constantType6.setName("FrecCard");

		this.constant1 = new Constant();
		this.constant1.setId(1);
		this.constant1.setConstantType(this.constantType1);
		this.constant1.setValue(52f);

		this.constant2 = new Constant();
		this.constant2.setId(2);
		this.constant2.setConstantType(this.constantType6);
		this.constant2.setValue(92f);

		this.constant1ToUpdate = new Constant();
		this.constant1ToUpdate.setId(1);
		this.constant1ToUpdate.setConstantType(this.constantType6);
		this.constant1ToUpdate.setValue(6f);

		Collection<Constant> constants = new ArrayList<>();
		constants.add(this.constant1);
		constants.add(this.constant2);

		this.consultation1 = new Consultation();
		this.consultation1.setId(1);
		this.consultation1.setConstants(constants);
	}

	private void initConstantValidatorTests(final int consultationId) {
		this.constantValidator = new ConstantValidator(this.consultationService, consultationId);
	}

	@Test
	void shouldNotValidateWhenConstantTypeIsNull() {

		Integer consultationId = 1;
		this.initConstantValidatorTests(consultationId);

		this.constant1.setConstantType(null);

		BindException errors = new BindException(this.constant1, "constant");
		this.constantValidator.validate(this.constant1, errors);

		Assertions.assertThat(errors.hasFieldErrors("constantType")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWhenConsultationConstantsIsNotEmpty() {

		Integer consultationId = 1;
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);
		this.initConstantValidatorTests(consultationId);

		BindException errors = new BindException(this.constant1, "constant");
		this.constantValidator.validate(this.constant1, errors);

		Assertions.assertThat(errors.hasFieldErrors("constantType")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldValidateWhenConsultationConstantsIsEmpty() {

		Integer consultationId = 1;
		this.consultation1.setConstants(new ArrayList<>());
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);
		this.initConstantValidatorTests(consultationId);

		BindException errors = new BindException(this.constant1, "constant");
		this.constantValidator.validate(this.constant1, errors);

		Assertions.assertThat(errors.hasFieldErrors("constantType")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateWhenConsultationAlreadyHasConstantType() {

		Integer consultationId = 1;
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);
		this.initConstantValidatorTests(consultationId);


		BindException errors = new BindException(this.constant1ToUpdate, "constant");
		this.constantValidator.validate(this.constant1ToUpdate, errors);

		Assertions.assertThat(errors.hasFieldErrors("constantType")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(floats = {-0.01f, -1f, -100f})
	void shouldNotValidateWhenConstantValueIsNegative(final float value) {

		Integer consultationId = 1;
		this.initConstantValidatorTests(consultationId);

		this.constant1.setValue(value);

		BindException errors = new BindException(this.constant1, "constant");
		this.constantValidator.validate(this.constant1, errors);

		Assertions.assertThat(errors.hasFieldErrors("value")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(floats = {0f, 1f, 1100f})
	void shouldValidateWhenConstantValueIs(final float value) {

		Integer consultationId = 1;
		this.initConstantValidatorTests(consultationId);

		this.constant1.setValue(value);

		BindException errors = new BindException(this.constant1, "constant");
		this.constantValidator.validate(this.constant1, errors);

		Assertions.assertThat(errors.hasFieldErrors("value")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}
}
