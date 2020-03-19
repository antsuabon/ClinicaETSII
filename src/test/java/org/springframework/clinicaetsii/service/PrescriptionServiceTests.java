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

}
