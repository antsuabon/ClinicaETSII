package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
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
	
	@Test
	void shouldValidatePrescription() {
		Doctor d = this.doctorService.findDoctorById(DOCTOR_ID);
		Patient p = this.patientService.findPatient(PATIENT_ID);
		Medicine m = this.medicineService.findMedicineById(MEDICINE_ID);
		
		Prescription pr1 = new Prescription();
		pr1.setDays(5);
		pr1.setDosage(0);
		pr1.setStartDate(LocalDateTime.now());
		pr1.setMedicine(m);
		pr1.setPatient(p);
		pr1.setDoctor(d);
		pr1.setPatientWarning("");
		pr1.setPharmaceuticalWarning("");
		
		Prescription pr2 = new Prescription();
		pr1.setDays(4);
		pr1.setDosage(100);
		pr1.setStartDate(LocalDateTime.now());
		pr1.setMedicine(m);
		pr1.setPatient(p);
		pr1.setDoctor(d);
		pr1.setPatientWarning("");
		pr1.setPharmaceuticalWarning("");
		
		Prescription pr3 = new Prescription();
		pr1.setDays(5);
		pr1.setDosage(20);
		pr1.setStartDate(LocalDateTime.now());
		pr1.setMedicine(null);
		pr1.setPatient(p);
		pr1.setDoctor(d);
		pr1.setPatientWarning("");
		pr1.setPharmaceuticalWarning("");
		
		Prescription pr4 = new Prescription();
		pr1.setDays(0);
		pr1.setDosage(20);
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
		
		
		prescriptionValidator.validate(pr2, errors);
		Assertions.assertThat(errors.hasFieldErrors("dosage")).isEqualTo(true);
		
		prescriptionValidator.validate(pr3, errors);
		Assertions.assertThat(errors.hasFieldErrors("medicine")).isEqualTo(true);
		
		prescriptionValidator.validate(pr4, errors);
		Assertions.assertThat(errors.hasFieldErrors("days")).isEqualTo(true);
		
	}
	
}
