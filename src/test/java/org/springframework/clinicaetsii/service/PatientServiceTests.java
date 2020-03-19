package org.springframework.clinicaetsii.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PatientServiceTests {
	
	@Autowired
	protected PatientService patientService;
	
	@Test
	void shouldShowValidPatient() {
		int id = 4;
		Patient patient = this.patientService.findPatient(id);
		Assertions.assertThat(patient).isNotNull();
		
		Assertions.assertThat(patient.getUsername()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getPassword()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getName()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getSurname()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getDni()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getEmail()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getPhone()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getNss()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getBirthDate()).isNotNull();
		Assertions.assertThat(patient.getAddress()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getState()).isNotNull().isNotBlank();
		Assertions.assertThat(patient.getGeneralPractitioner()).isNotNull();
		
	}

}
