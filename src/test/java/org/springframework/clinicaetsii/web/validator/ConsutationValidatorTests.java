package org.springframework.clinicaetsii.web.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.validation.BindException;

@ExtendWith(MockitoExtension.class)
public class ConsutationValidatorTests {

	@Mock
	private AppointmentService appointmentService;

	@Mock
	private ConsultationService consultationService;

	private ConsultationValidator consultationValidator;

	private void initConsultationValidator(final Integer appointmentId, final Integer consultationId) {
		this.consultationValidator = new ConsultationValidator(this.appointmentService, this.consultationService, appointmentId, consultationId);
	}

	private Doctor doctor3;
	private Patient patient1;

	private ConstantType constantType1;
	private ConstantType constantType6;
	private Constant constant1;
	private Constant constant2;
	private Appointment appointment1;
	private DischargeType dischargeType1;
	private Examination examination1;

	private Consultation consultation1;

	@BeforeEach
	private void initConsultations() {

		this.doctor3 = new Doctor();
		this.doctor3.setId(3);
		this.doctor3.setUsername("doctor3");
		this.doctor3.setPassword("doctor3");
		this.doctor3.setEnabled(true);
		this.doctor3.setName("Antonio");
		this.doctor3.setSurname("Suarez Bono");
		this.doctor3.setDni("45612378P");
		this.doctor3.setEmail("antonio@gmail.com");
		this.doctor3.setPhone("955668756");
		this.doctor3.setCollegiateCode("303024345");

		this.patient1 = new Patient();
		this.patient1.setId(1);
		this.patient1.setAddress("Calle Oscar Arias");
		this.patient1.setBirthDate(LocalDate.now());
		this.patient1.setDni("41235678L");
		this.patient1.setEmail("pedro@gmail.com");
		this.patient1.setEnabled(true);
		this.patient1.setGeneralPractitioner(this.doctor3);
		this.patient1.setName("Pedro");
		this.patient1.setNss("12345778S");
		this.patient1.setPassword("patient1");
		this.patient1.setPhone("123456789");
		this.patient1.setState("España");
		this.patient1.setSurname("Roldán");
		this.patient1.setUsername("patient1");

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

		Collection<Constant> constants = new ArrayList<>();
		constants.add(this.constant1);
		constants.add(this.constant2);

		this.appointment1 = new Appointment();
		this.appointment1.setId(1);
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);
		this.appointment1.setStartTime(LocalDateTime.of(2020, 3, 2, 11, 00));
		this.appointment1.setEndTime(LocalDateTime.of(2020, 3, 2, 11, 07));

		this.dischargeType1 = new DischargeType();
		this.dischargeType1.setId(1);
		this.dischargeType1.setName("Domicilio");

		this.examination1 = new Examination();
		this.examination1.setId(1);
		this.examination1.setDescription("Tiene el vientre muy hinchado");
		this.examination1.setStartTime(LocalDateTime.of(2020, 3, 2, 11, 02));

		Collection<Examination> examinations = new ArrayList<>();
		examinations.add(this.examination1);

		this.consultation1 = new Consultation();
		this.consultation1.setId(1);
		this.consultation1.setConstants(constants);
		this.consultation1.setAppointment(this.appointment1);
		this.consultation1.setStartTime(LocalDateTime.of(2020, 3, 2, 11, 01));
		this.consultation1.setEndTime(LocalDateTime.of(2020, 3, 2, 11, 07));
		this.consultation1.setExaminations(examinations);
	}

	@Test
	void shouldNotValidateWhenStartTimeIsNull() {

		Integer appointmentId = 1;
		Integer consultationId = 1;
		this.initConsultationValidator(appointmentId, consultationId);
		this.consultation1.setStartTime(null);
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("startTime")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWhenStartTimeIsAfterAppointmentStartTime() {

		Integer appointmentId = 1;
		this.initConsultationValidator(appointmentId, null);
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("startTime")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldValidateWhenDischargeTypeIsPresentAndExaminationsNotEmpty() {

		Integer appointmentId = 1;
		Integer consultationId = 1;
		this.initConsultationValidator(appointmentId, consultationId);
		this.consultation1.setDischargeType(this.dischargeType1);
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("dischargeType")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldValidateWhenStartTimeIsEqualsToAppointmentStartTime() {

		Integer appointmentId = 1;
		this.initConsultationValidator(appointmentId, null);
		this.consultation1.setStartTime(this.appointment1.getStartTime());
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("startTime")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	@Test
	void shouldNotValidateWhenStartTimeAfterAppointmentStartTime() {

		Integer appointmentId = 1;
		this.initConsultationValidator(appointmentId, null);
		this.consultation1.setStartTime(this.appointment1.getStartTime().minusMinutes(1));
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("startTime")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWhenDischargeTypeIsPresentAndNotNewAndExaminationsIsNull() {

		Integer appointmentId = 1;
		Integer consultationId = 1;
		this.initConsultationValidator(appointmentId, consultationId);
		this.consultation1.setExaminations(null);
		this.consultation1.setDischargeType(this.dischargeType1);
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("dischargeType")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldNotValidateWhenDischargeTypeIsPresentAndNotNewAndExaminationsIsEmpty() {

		Integer appointmentId = 1;
		Integer consultationId = 1;
		this.initConsultationValidator(appointmentId, consultationId);
		this.consultation1.setExaminations(new ArrayList<>());
		this.consultation1.setDischargeType(this.dischargeType1);
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("dischargeType")).isTrue();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	@Test
	void shouldValidateWhenDischargeTypeIsPresentAndNew() {

		Integer appointmentId = 1;
		Integer consultationId = 1;
		this.initConsultationValidator(appointmentId, consultationId);
		this.consultation1.setId(null);
		this.consultation1.setDischargeType(this.dischargeType1);
		Mockito.when(this.appointmentService.findAppointmentById(appointmentId)).thenReturn(this.appointment1);
		Mockito.when(this.consultationService.findConsultationById(consultationId)).thenReturn(this.consultation1);

		BindException errors = new BindException(this.consultation1, "consultation");
		this.consultationValidator.validate(this.consultation1, errors);

		Assertions.assertThat(errors.hasFieldErrors("dischargeType")).isFalse();
		Assertions.assertThat(errors.getErrorCount()).isEqualTo(0);
	}

}
