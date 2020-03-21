package org.springframework.clinicaetsii.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PrescriptionServiceTests {
	
	@Autowired
	protected PrescriptionService prescriptionService;
	
	@Autowired
	protected MedicineService medicineService;
	
	@Autowired
	protected DoctorService doctorService;
	
	@Autowired
	protected PatientService patientService;
	
	protected
	
	@Test
	void shouldInsertPrescription() {
		Collection<Prescription> prescriptions1 = this.prescriptionService.findAllPrescriptions();
		
		int initSize = prescriptions1.size();
		
		Medicine medicine1 = this.medicineService.findMedicineById(1);
		Patient patient1 = this.patientService.findPatientById(4);
		Doctor doctor1 = this.doctorService.findDoctorById(1);
		
		LocalDateTime inicio = LocalDateTime.now();
		
		Prescription prescription = new Prescription();
		prescription.setDays(3f);
		prescription.setDosage(3f);
		prescription.setPatientWarning("");
		prescription.setPharmaceuticalWarning("");
		
		prescription.setStartDate(inicio);
		prescription.setDoctor(doctor1);
		prescription.setPatient(patient1);
		prescription.setMedicine(medicine1);
		
		this.prescriptionService.savePrescription(prescription);
		Assertions.assertThat(prescription.getId().longValue()).isNotEqualTo(0);
		
		Collection<Prescription> prescriptions2 = this.prescriptionService.findAllPrescriptions();
		int finalSize = prescriptions2.size();
		Assertions.assertThat(finalSize).isEqualTo(initSize + 1); 
		
	}

}
