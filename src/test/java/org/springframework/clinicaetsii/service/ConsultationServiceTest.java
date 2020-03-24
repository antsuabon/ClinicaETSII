
package org.springframework.clinicaetsii.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ConsultationServiceTest {

	@Autowired
	protected ConsultationService consultationService;

	@Autowired
	protected ExaminationService examinationService;

	@Autowired
	protected AppointmentService appoinService;


	@Test
	void shouldFindConsultationByPatientId() {
		int patientId1 = 4;
		Collection<Consultation> consultations1 = this.consultationService.findConsultationsByPatientId(patientId1);

		Boolean condition = true;
		for (Consultation c : consultations1) {

			if (c.getAppointment().getPatient().getId() != patientId1) {
				condition = false;
				break;
			}

		}

		Assertions.assertThat(condition).isTrue();

		int patientId2 = -1;
		Collection<Consultation> consultations2 = this.consultationService.findConsultationsByPatientId(patientId2);

		Assertions.assertThat(consultations2).isEmpty();

	}

	@Test
	void shouldFindConsultationById() {
		int consultationId1 = 1;
		Consultation consultation1 = this.consultationService.findConsultationById(consultationId1);
		Assertions.assertThat(consultation1.getId()).isEqualTo(consultationId1);

		int consultationId2 = -1;
		Consultation consultation2 = this.consultationService.findConsultationById(consultationId2);
		Assertions.assertThat(consultation2).isNull();
	}

	@Test
	void shouldFindAllDiagnoses() {
		Collection<Diagnosis> diagnoses = this.consultationService.findAllDiagnoses();
		Assertions.assertThat(diagnoses)
		.isNotEmpty()
		.allMatch(s -> s.getId() != null)
		.allMatch(s -> s.getName() != null)
		.allMatch(s -> !s.getName().isEmpty());
	}

	@Test
	void shouldFindAllDischargeTypes() {
		Collection<DischargeType> dischargeTypes = this.consultationService.findDischargeTypes();
		Assertions.assertThat(dischargeTypes)
		.isNotEmpty()
		.allMatch(s -> s.getId() != null)
		.allMatch(s -> s.getName() != null)
		.allMatch(s -> !s.getName().isEmpty());
	}

	@Test
	@Transactional
	void shouldInsertConsultation() {
		Consultation consultation = new Consultation();
//			consultation.setConstants(constants);
			Appointment appointment = this.appoinService.findAppointmentById(4);
			Assertions.assertThat(appointment).isNotNull();
			consultation.setAppointment(appointment);

		consultation.setStartTime(LocalDateTime.of(2020, 3, 2, 11, 01));
		consultation.setEndTime(LocalDateTime.of(2020, 3, 2, 11, 07));
		consultation.setExaminations(new ArrayList<>());
		consultation.setAnamnesis("Dolor de estómago");
		consultation.setRemarks("Fiebres altas");

		this.consultationService.save(consultation);
		Assertions.assertThat(consultation.getId()).isNotNull().isGreaterThan(0);

		consultation = this.consultationService.findConsultationById(consultation.getId());
		Assertions.assertThat(consultation).isNotNull();
		Assertions.assertThat(consultation.getAppointment()).isNotNull();
		Assertions.assertThat(consultation.getAppointment().getId()).isEqualTo(4);
		Assertions.assertThat(consultation.getAnamnesis()).isNotNull().isEqualTo("Dolor de estómago");
		Assertions.assertThat(consultation.getRemarks()).isNotNull().isEqualTo("Fiebres altas");
	}

	void shouldUpdateConsultation() {

	}

}
