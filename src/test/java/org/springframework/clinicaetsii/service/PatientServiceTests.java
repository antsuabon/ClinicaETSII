package org.springframework.clinicaetsii.service;

import java.util.Collection;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PatientServiceTests {

	@Autowired
	protected PatientService patientService;


	@Test
@WithMockUser(username = "doctor1", roles = {"doctor"})
void shouldFindCurrentDoctorPatients() {

	Collection<Patient> patients = this.patientService.findCurrentDoctorPatients();
	boolean condition1 = true;

	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	UserDetails user = (UserDetails) principal;

	for (Patient patient : patients) {

		if (!patient.getGeneralPractitioner().getUsername().equals(user.getUsername())) {
			condition1 = false;
			break;
		}

	}

	Assertions.assertThat(condition1).isTrue();

	boolean condition2 = true;
	for (Patient patient : patients) {

		if (!patient.getGeneralPractitioner().getUsername().equals("doctor2")) {
			condition2 = false;
			break;
		}

	}

	Assertions.assertThat(condition2).isFalse();

}

@Test
@WithMockUser(username = "patient1", roles = {"patient"})

void shouldFindCurrentDoctorPatientsAuthority() {

	Collection<Patient> patients = this.patientService.findCurrentDoctorPatients();
	Assertions.assertThat(patients).isEmpty();
}

	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void shouldFindCurrentPatient() {
		Patient patient1 = this.patientService.findCurrentPatient();
		Assertions.assertThat(patient1.getId()).isEqualTo(4);
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldNotFindCurrentPatient() {
		Patient patient1 = this.patientService.findCurrentPatient();
		Assertions.assertThat(patient1).isEqualTo(null);
	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void shouldFindPatientByUsername() {
		Patient patient1 = this.patientService.findPatientByUsername();
		Assertions.assertThat(patient1.getId()).isEqualTo(4);
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldNotFindPatientByUsername() {
		Patient patient1 = this.patientService.findPatientByUsername();
		Assertions.assertThat(patient1).isEqualTo(null);
	}
	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})

	void shouldFindPatientByPatientId() {
		Patient patient1 = this.patientService.findPatient(4);
		Assertions.assertThat(patient1.getId()).isEqualTo(4);

	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})

	void shouldNotFindPatientByPatientId() {
		Patient patient2 = this.patientService.findPatient(-1);
		Assertions.assertThat(patient2).isEqualTo(null);
	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void shouldFindAllPatients() {
		Collection<Patient> patients = this.patientService.findPatients();
		Assertions.assertThat(patients.size()).isEqualTo(2);
	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void shouldFindDoctorByPatient() {
		int doctorId = 1;
		int patientId = 4;

		Doctor doctor = this.patientService.findDoctorByPatient(patientId);

		Assertions.assertThat(doctor.getId()).isEqualTo(doctorId);
	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void shouldNotFindDoctorByPatient() {

		Doctor doctor2 = this.patientService.findDoctorByPatient(-1);

		Assertions.assertThat(doctor2).isEqualTo(null);
	}


	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void patientShouldFindCurrentPatient() {

		Patient patient = this.patientService.findCurrentPatient();

		Assertions.assertThat(patient).isNotNull();

		String username = patient.getUsername();

		Assertions.assertThat(username.equals("patient1"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void doctorShouldNotFindCurrentPatient() {

		Patient patient = this.patientService.findCurrentPatient();

		Assertions.assertThat(patient).isNull();

	}

	@Test
	@WithMockUser(username = "administrative1", roles = {"administrative"})
	void administrativeShouldNotFindCurrentPatient() {

		Patient patient = this.patientService.findCurrentPatient();

		Assertions.assertThat(patient).isNull();

	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotFindCurrentPatient() {

		Patient patient = this.patientService.findCurrentPatient();

		Assertions.assertThat(patient).isNull();
	}

	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void shouldFindAppointmentDeleteByPatientUsername() {

		Collection<Appointment> appointment1 = this.patientService.findAppointmentsDelete();
		Boolean condition = true;

		for (Appointment a : appointment1) {

			if (!a.getPatient().getUsername().equals("patient1")) {
				condition = false;
				break;
			}

		}

		Assertions.assertThat(condition).isTrue();

		}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void shouldNotFindAppointmentDeleteByPatientUsername() {

		Collection<Appointment> appointment1 = this.patientService.findAppointmentsDelete();

		Collection<Appointment> ca = new HashSet<>();

		for (Appointment a : appointment1) {

			if (a.getPatient().getUsername().equals("doctor1")) {
				ca.add(a);
			}

		}

		Assertions.assertThat(ca).isEmpty();

	}

	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void shouldFindAppointmentDoneByPatientUsername() {

		Collection<Appointment> appointment1 = this.patientService.findAppointmentsDone();
		Boolean condition = true;

		for (Appointment a : appointment1) {

			if (!a.getPatient().getUsername().equals("patient1")) {
				condition = false;
				break;
			}

		}

		Assertions.assertThat(condition).isTrue();

		}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void shouldNotFindAppointmentDoneByPatientUsername() {

		Collection<Appointment> appointment1 = this.patientService.findAppointmentsDone();

		Collection<Appointment> ca = new HashSet<>();

		for (Appointment a : appointment1) {

			if (a.getPatient().getUsername().equals("doctor1")) {
				ca.add(a);
			}

		}

		Assertions.assertThat(ca).isEmpty();

	}


}
