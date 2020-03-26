package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PrescriptionServiceTests {

	@Autowired
	protected PrescriptionService prescriptionService;

	@Test
	void shouldListPrescriptionsOfPatient() {
		String username = "patient1";
		Collection<Prescription> prescriptions = this.prescriptionService.findPrescriptionsByPatientUsername(username);
		Assertions.assertThat(prescriptions.size()).isEqualTo(2);
	}

	@Test
	void shouldShowValidPrescription() {
		int prescriptionId = 1;
		Prescription prescription = this.prescriptionService.findPrescriptionById(prescriptionId);
		Assertions.assertThat(prescription).isNotNull();

		Assertions.assertThat(prescription.getStartDate()).isNotNull();
		Assertions.assertThat(prescription.getDosage()).isGreaterThan(0f);
		Assertions.assertThat(prescription.getDays()).isGreaterThan(0f);
		Assertions.assertThat(prescription.getMedicine()).isNotNull();
		Assertions.assertThat(prescription.getPatient()).isNotNull();
		Assertions.assertThat(prescription.getDoctor()).isNotNull();
	}

	@Test
	void shouldFindPrescriptionsByPatientId() {

		int patientId1 = 4;
		Collection<Prescription> prescriptions1 = this.prescriptionService.findPrescriptionsByPatientId(patientId1);
		boolean condition = true;

		for (Prescription prescription : prescriptions1) {

			if (!(prescription.getPatient().getId() == patientId1)) {
				condition = false;
				break;

			}

		}

		Assertions.assertThat(condition).isTrue();

		int patientId2 = -1;
		Collection<Prescription> prescriptions2 = this.prescriptionService.findPrescriptionsByPatientId(patientId2);
		Assertions.assertThat(prescriptions2).isEmpty();

	}

	@Test
	void shouldFindPrescriptionById() {
		int prescriptionId1 = 1;
		Prescription prescription1 = this.prescriptionService.findPrescriptionById(prescriptionId1);
		Assertions.assertThat(prescription1.getId() == prescriptionId1).isTrue();

		int prescriptionId2 = -1;
		Prescription prescription2 = this.prescriptionService.findPrescriptionById(prescriptionId2);
		Assertions.assertThat(prescription2).isEqualTo(null);
	}

	@Test
	void shouldDeletePrescription() {

		int prescriptionId1 = 1;
		Prescription prescription1 = this.prescriptionService.findPrescriptionById(prescriptionId1);
		this.prescriptionService.deletePrescription(prescription1);
		Prescription prescriptionDeleted = this.prescriptionService.findPrescriptionById(prescriptionId1);
		Assertions.assertThat(prescriptionDeleted).isEqualTo(null);

	}

}
