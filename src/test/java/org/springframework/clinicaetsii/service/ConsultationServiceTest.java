
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ConsultationServiceTest {

	@Autowired
	protected ConsultationService consultationService;


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

}
