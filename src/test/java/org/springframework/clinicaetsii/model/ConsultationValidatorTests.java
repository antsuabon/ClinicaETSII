package org.springframework.clinicaetsii.model;

import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ConsultationValidatorTests {

	Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	private Consultation consultation1;

	@BeforeEach
	private void initConsultation() {
//		this.consultation1 = new Consultation();
//		this.consultation1.setId(1);
//		this.consultation1.setStartTime(startTime);
//		this.consultation1.setEndTime(endTime);
//		this.consultation1.setAnamnesis(anamnesis);
//		this.consultation1.setRemarks(remarks);
//		this.consultation1.setDischargeType(dischargeType);
//		this.consultation1.setAppointment(appointment);
//		this.consultation1.setDiagnoses(diagnoses);
//		this.consultation1.setExaminations(examinations);
	}
}
