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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class DoctorServiceTests {

	@Autowired
	protected DoctorService doctorService;


	@Test
	void shouldListDoctorsSortedByServices() {
		Collection<Doctor> doctors = this.doctorService.findDoctorsSortedByNumOfServices();
		Assertions.assertThat(doctors.size()).isEqualTo(3);

		List<Doctor> listDoctors = new ArrayList<>(doctors);
		Doctor firstDoctor = listDoctors.get(0);
		Doctor lastDoctor = listDoctors.get(listDoctors.size() - 1);
		Boolean sortedFirst = true;
		Boolean sortedLast = true;
		for (Doctor d : listDoctors) {
			if (d.getServices().size() > firstDoctor.getServices().size()) {
				sortedFirst = false;
				break;
			}
			if (d.getServices().size() < lastDoctor.getServices().size()) {
				sortedLast = false;
				break;
			}
		}
		Assertions.assertThat(sortedFirst && sortedLast).isTrue();
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void doctorShouldFindCurrentDoctor() {
		Doctor currentDoctor = this.doctorService.findCurrentDoctor();

		Assertions.assertThat(currentDoctor).isNotNull();

		String username = currentDoctor.getUsername();

		Assertions.assertThat(username.equals("doctor1"));
	}


	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void patientShouldNotFindCurrentDoctor() {
		Doctor currentDoctor = this.doctorService.findCurrentDoctor();

		Assertions.assertThat(currentDoctor).isNull();
	}

	@Test
	@WithMockUser(username = "administrative1", roles = {"administrative"})
	void administrativeShouldNotFindCurrentDoctor() {
		Doctor currentDoctor = this.doctorService.findCurrentDoctor();

		Assertions.assertThat(currentDoctor).isNull();
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotFindCurrentDoctor() {
		Doctor currentDoctor = this.doctorService.findCurrentDoctor();

		Assertions.assertThat(currentDoctor).isNull();
	}

	@Test
	void shouldFindAllServices() {
		Collection<org.springframework.clinicaetsii.model.Service> services = this.doctorService.findAllServices();

		Assertions.assertThat(services)
			.isNotEmpty()
			.allMatch(s -> !s.getId().equals(null), "")
			.allMatch(s -> !s.getName().equals(null), "")
			.allMatch(s -> !s.getName().isEmpty(), "");
	}



}
