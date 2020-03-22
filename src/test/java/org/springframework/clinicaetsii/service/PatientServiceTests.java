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
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

}
