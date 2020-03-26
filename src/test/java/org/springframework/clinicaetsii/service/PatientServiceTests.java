/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PatientServiceTests {

	@Autowired
	protected PatientService patientService;


	@Test
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

}
