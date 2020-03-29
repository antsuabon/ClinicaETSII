package org.springframework.clinicaetsii.web.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.web.validator.PrescriptionValidator;
import org.springframework.validation.BindException;

@ExtendWith(MockitoExtension.class)
public class PrescriptionValidatorTests {


	protected PrescriptionValidator prescriptionValidator;

	private Doctor doctor1;
	private Patient patient1;
	private Medicine medicine1;

	@BeforeEach
	void setup() {

		this.doctor1 = new Doctor();
		this.doctor1.setId(1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Antonio");
		this.doctor1.setSurname("Suarez Bono");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("antonio@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303024345");

		this.patient1 = new Patient();
		this.patient1.setId(1);
		this.patient1.setAddress("Calle Oscar Arias");
		this.patient1.setBirthDate(LocalDate.of(1998, 8, 12));
		this.patient1.setDni("41235678L");
		this.patient1.setEmail("pedro@gmail.com");
		this.patient1.setEnabled(true);
		this.patient1.setGeneralPractitioner(this.doctor1);
		this.patient1.setName("Pedro");
		this.patient1.setNss("12345778S");
		this.patient1.setPassword("patient1");
		this.patient1.setPhone("123456789");
		this.patient1.setState("España");
		this.patient1.setSurname("Roldán");
		this.patient1.setUsername("patient1");

		this.medicine1 = new Medicine();
		this.medicine1.setId(1);
		this.medicine1.setCommercialName("Ibuprofeno");
		this.medicine1.setGenericalName("Dalsy");
		this.medicine1.setQuantity(1f);
		this.medicine1.setContraindications("Dolor leve y moderado");
		this.medicine1.setIndications(
				"En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.");
	}

	@Test
	void shouldValidatePrescriptionDosage() {

		Prescription pr1 = new Prescription();
		pr1.setDays(5);
		pr1.setDosage(0);
		pr1.setStartDate(LocalDateTime.now());
		pr1.setMedicine(this.medicine1);
		pr1.setPatient(this.patient1);
		pr1.setDoctor(this.doctor1);
		pr1.setPatientWarning("");
		pr1.setPharmaceuticalWarning("");

		this.prescriptionValidator = new PrescriptionValidator();

		BindException errors = new BindException(pr1, "prescription");

		this.prescriptionValidator.validate(pr1, errors);
		Assertions.assertThat(errors.hasFieldErrors("dosage")).isEqualTo(true);

	}

	@Test
	void shouldValidatePrescriptionDosage2() {

		Prescription pr2 = new Prescription();
		pr2.setDays(4);
		pr2.setDosage(100);
		pr2.setStartDate(LocalDateTime.now());
		pr2.setMedicine(this.medicine1);
		pr2.setPatient(this.patient1);
		pr2.setDoctor(this.doctor1);
		pr2.setPatientWarning("");
		pr2.setPharmaceuticalWarning("");

		this.prescriptionValidator = new PrescriptionValidator();

		BindException errors = new BindException(pr2, "prescription");

		this.prescriptionValidator.validate(pr2, errors);
		Assertions.assertThat(errors.hasFieldErrors("dosage")).isEqualTo(true);


	}

	@Test
	void shouldValidatePrescriptionMedicine() {

		Prescription pr3 = new Prescription();
		pr3.setDays(5);
		pr3.setDosage(20);
		pr3.setStartDate(LocalDateTime.now());
		pr3.setMedicine(null);
		pr3.setPatient(this.patient1);
		pr3.setDoctor(this.doctor1);
		pr3.setPatientWarning("");
		pr3.setPharmaceuticalWarning("");

		this.prescriptionValidator = new PrescriptionValidator();

		BindException errors = new BindException(pr3, "prescription");

		this.prescriptionValidator.validate(pr3, errors);
		Assertions.assertThat(errors.hasFieldErrors("medicine")).isEqualTo(true);

	}

	@Test
	void shouldValidatePrescriptionDays() {

		Prescription pr4 = new Prescription();
		pr4.setDays(0);
		pr4.setDosage(20);
		pr4.setStartDate(LocalDateTime.now());
		pr4.setMedicine(this.medicine1);
		pr4.setPatient(this.patient1);
		pr4.setDoctor(this.doctor1);
		pr4.setPatientWarning("");
		pr4.setPharmaceuticalWarning("");

		this.prescriptionValidator = new PrescriptionValidator();

		BindException errors = new BindException(pr4, "prescription");

		this.prescriptionValidator.validate(pr4, errors);
		Assertions.assertThat(errors.hasFieldErrors("days")).isEqualTo(true);

	}

}
