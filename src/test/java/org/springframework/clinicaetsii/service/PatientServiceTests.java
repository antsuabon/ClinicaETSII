package org.springframework.clinicaetsii.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PatientServiceTests {

	@Autowired
	protected DoctorService doctorService;
	
	@Autowired
	protected PatientService patientService;


	@Test
	void shouldListDoctorsServices() {
		
		Collection<Doctor> doctors = this.doctorService.findAllDoctors();

		List<Doctor> listDoctors = new ArrayList<>(doctors);
		boolean all = listDoctors.size() == 3;
		
		
		Assertions.assertThat(all);

	}
	
	@Test
	void shouldUpdatePatient() {
		Collection<Patient> patients1 = this.patientService.findPatients();
		int initSize = patients1.size();
		
		Patient patient1 = this.patientService.findPatientById(4);
		Doctor doctor1 = this.doctorService.findDoctorById(2);
		
		patient1.setGeneralPractitioner(doctor1);
		
		this.patientService.savePatient(patient1);
		
		Collection<Patient> patients2 = this.patientService.findPatients();
		int finalSize = patients2.size();
		
		Assertions.assertThat(initSize).isEqualTo(finalSize);
	}
	
}
