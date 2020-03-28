package org.springframework.clinicaetsii.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ConsultationValidatorTests {

	Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Doctor doctor1;
	private Patient patient1;
	private Appointment appointment1;
	private Consultation consultation1;
	private DischargeType dischargeType2;

	@BeforeEach
	private void initConsultation() {

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

		this.appointment1 = new Appointment();
		this.appointment1.setStartTime(LocalDateTime.now());
		this.appointment1.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.dischargeType2 = new DischargeType();
		this.dischargeType2.setId(2);
		this.dischargeType2.setName("Especialidad");

		this.consultation1 = new Consultation();
		this.consultation1.setId(1);
		this.consultation1.setStartTime(LocalDateTime.of(2020, 3, 7, 11, 0));
		this.consultation1.setEndTime(LocalDateTime.of(2020, 3, 7, 11, 7));
		this.consultation1.setAnamnesis("Dolor de estómago");
		this.consultation1.setRemarks("Fiebres altas");
		this.consultation1.setDischargeType(this.dischargeType2);
		this.consultation1.setAppointment(this.appointment1);

		Diagnosis diagnosis1 = new Diagnosis();
		diagnosis1.setId(1);
		diagnosis1.setName("Gripe invernal");

		Collection<Diagnosis> diagnoses = new ArrayList<>();
		diagnoses.add(diagnosis1);

		this.consultation1.setDiagnoses(diagnoses);

		Examination examination1 = new Examination();
		examination1.setId(1);
		examination1.setDescription("Tiene el vientre muy hinchado");
		Collection<Examination> examinations = new ArrayList<>();
		examinations.add(examination1);

		this.consultation1.setExaminations(examinations);
	}

	@Test
	void shouldValidateConsultation() {

		Validator validator = createValidator();
		Set<ConstraintViolation<Consultation>> constraintViolations =
				validator.validate(this.consultation1);

		Assertions.assertThat(constraintViolations).isEmpty();
	}
}
