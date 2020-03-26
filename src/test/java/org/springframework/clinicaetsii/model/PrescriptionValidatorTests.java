package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.web.validator.PrescriptionValidator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.BindException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(org.springframework.stereotype.Service.class))
@ExtendWith(MockitoExtension.class)
public class PrescriptionValidatorTests {
	
	private final int DOCTOR_ID = 4;
	private final int PATIENT_ID = 1;
	private final int MEDICINE_ID = 1;

	@Autowired
	protected PrescriptionService prescriptionService;
	
	@Autowired
	protected MedicineService medicineService;
	
	@Autowired
	protected DoctorService doctorService;
	
	@Autowired
	protected PatientService patientService;
	
	protected PrescriptionValidator prescriptionValidator;
	
	private Doctor d;
	private Patient p;
	private Medicine m;
	
	@BeforeEach
	void setup() {
		d = this.doctorService.findDoctorById(DOCTOR_ID);
		p = this.patientService.findPatient(PATIENT_ID);
		m = this.medicineService.findMedicineById(MEDICINE_ID);
	}
	
	@Test
	void shouldValidatePrescriptionDosage() {
		
		Prescription pr1 = new Prescription();
		pr1.setDays(5);
		pr1.setDosage(0);
		pr1.setStartDate(LocalDateTime.now());
		pr1.setMedicine(m);
		pr1.setPatient(p);
		pr1.setDoctor(d);
		pr1.setPatientWarning("");
		pr1.setPharmaceuticalWarning("");
			
		this.prescriptionValidator = new PrescriptionValidator();
		
		BindException errors = new BindException(pr1, "prescription");
		
		prescriptionValidator.validate(pr1, errors);
		Assertions.assertThat(errors.hasFieldErrors("dosage")).isEqualTo(true);
		
	}
	
	@Test
	void shouldValidatePrescriptionDosage2() {
		
		Prescription pr2 = new Prescription();
		pr2.setDays(4);
		pr2.setDosage(100);
		pr2.setStartDate(LocalDateTime.now());
		pr2.setMedicine(m);
		pr2.setPatient(p);
		pr2.setDoctor(d);
		pr2.setPatientWarning("");
		pr2.setPharmaceuticalWarning("");
			
		this.prescriptionValidator = new PrescriptionValidator();
		
		BindException errors = new BindException(pr2, "prescription");
		
		prescriptionValidator.validate(pr2, errors);
		Assertions.assertThat(errors.hasFieldErrors("dosage")).isEqualTo(true);
		
		
	}
	
	@Test
	void shouldValidatePrescriptionMedicine() {
		
		Prescription pr3 = new Prescription();
		pr3.setDays(5);
		pr3.setDosage(20);
		pr3.setStartDate(LocalDateTime.now());
		pr3.setMedicine(null);
		pr3.setPatient(p);
		pr3.setDoctor(d);
		pr3.setPatientWarning("");
		pr3.setPharmaceuticalWarning("");
			
		this.prescriptionValidator = new PrescriptionValidator();
		
		BindException errors = new BindException(pr3, "prescription");
		
		prescriptionValidator.validate(pr3, errors);
		Assertions.assertThat(errors.hasFieldErrors("medicine")).isEqualTo(true);
		
	}
	
	@Test
	void shouldValidatePrescriptionDays() {
		
		Prescription pr4 = new Prescription();
		pr4.setDays(0);
		pr4.setDosage(20);
		pr4.setStartDate(LocalDateTime.now());
		pr4.setMedicine(m);
		pr4.setPatient(p);
		pr4.setDoctor(d);
		pr4.setPatientWarning("");
		pr4.setPharmaceuticalWarning("");
			
		this.prescriptionValidator = new PrescriptionValidator();
		
		BindException errors = new BindException(pr4, "prescription");
		
		prescriptionValidator.validate(pr4, errors);
		Assertions.assertThat(errors.hasFieldErrors("days")).isEqualTo(true);
		
	}
	
}
