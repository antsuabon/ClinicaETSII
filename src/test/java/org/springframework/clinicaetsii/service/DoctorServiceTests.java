/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.springframework.clinicaetsii.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.service.exceptions.DeleteDoctorException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DoctorServiceTests {

	@Autowired
	protected DoctorService doctorService;

	@PersistenceContext
	private EntityManager entityManager;


	@Test
	void shouldListDoctorsSortedByServices() {
		Collection<Doctor> doctors = this.doctorService.findDoctorsSortedByNumOfServices();
		Assertions.assertThat(doctors.size()).isEqualTo(5);

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
	void shouldListDoctorsSortedByServicesAndWithServices() {
		Collection<Doctor> doctors = this.doctorService.findAllDoctorsWithServices();
		Assertions.assertThat(doctors.size()).isEqualTo(5);

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

		Assertions.assertThat(username).isEqualTo("doctor1");

	}

	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void patientShouldNotFindCurrentDoctor() {
		Doctor currentDoctor = this.doctorService.findCurrentDoctor();

		Assertions.assertThat(currentDoctor).isNull();
	}

	@Test
	void shouldFindByUsername() {
		Doctor doctor = this.doctorService.findDoctorByUsername("doctor1");

		Assertions.assertThat(doctor).isNotNull();

		Assertions.assertThat(doctor.getUsername()).isNotNull().isEqualTo("doctor1");
	}

	@Test
	void shouldNotFindByUsername() {
		Doctor doctor = this.doctorService.findDoctorByUsername("notAnUsername");

		Assertions.assertThat(doctor).isNull();

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
		Collection<org.springframework.clinicaetsii.model.Service> services =
				this.doctorService.findAllServices();

		Assertions.assertThat(services).isNotEmpty().allMatch(s -> s.getId() != null)
				.allMatch(s -> s.getName() != null).allMatch(s -> !s.getName().isEmpty());
	}

	@Test
	@Transactional
	void shouldUpdateDoctor() {
		Doctor doctor = new Doctor();
		doctor.setId(1);
		doctor.setUsername("doctorPrueba");
		doctor.setPassword("doctorPrueba");
		doctor.setEnabled(true);
		doctor.setName("María");
		doctor.setSurname("Laso Escot");
		doctor.setDni("12345678S");
		doctor.setEmail("maria@gmail.com");
		doctor.setPhone("956787025");
		doctor.setCollegiateCode("303092345");

		this.doctorService.save(doctor);

		doctor = this.doctorService.findDoctorById(1);
		Assertions.assertThat(doctor).isNotNull();
		Assertions.assertThat(doctor.getUsername()).isEqualTo("doctorPrueba");
		Assertions.assertThat(doctor.getPassword()).isEqualTo("doctorPrueba");
		Assertions.assertThat(doctor.isEnabled()).isTrue();
		Assertions.assertThat(doctor.getName()).isEqualTo("María");
		Assertions.assertThat(doctor.getSurname()).isEqualTo("Laso Escot");
		Assertions.assertThat(doctor.getDni()).isEqualTo("12345678S");
		Assertions.assertThat(doctor.getEmail()).isEqualTo("maria@gmail.com");
		Assertions.assertThat(doctor.getPhone()).isEqualTo("956787025");
		Assertions.assertThat(doctor.getCollegiateCode()).isEqualTo("303092345");
	}

	@Test
	@Transactional
	void shouldNotUpdateDoctorWithExistingUsername() {

		Doctor doctor = new Doctor();
		doctor.setId(1);
		doctor.setUsername("doctor2");
		doctor.setPassword("doctorPrueba");
		doctor.setEnabled(true);
		doctor.setName("Pablo");
		doctor.setSurname("Rodriguez Garrido");
		doctor.setDni("23455558N");
		doctor.setEmail("pablo@gmail.com");
		doctor.setPhone("956784325");
		doctor.setCollegiateCode("303012345");

		Assertions.assertThatThrownBy(() -> {
			this.doctorService.save(doctor);
			this.entityManager.flush();
		}).isInstanceOf(PersistenceException.class)
				.hasCauseInstanceOf(ConstraintViolationException.class);

	}

	@Test
	void shouldListDoctors() {
		Collection<Doctor> doctors = this.doctorService.findAllDoctors();
		Assertions.assertThat(doctors.size()).isEqualTo(5);

	}

	@Test
	void shouldFindDoctor() {
		Doctor doctor1 = this.doctorService.findDoctorById(1);
		String username = doctor1.getUsername();

		Assertions.assertThat(username).isEqualTo("doctor1");

		Doctor doctor2 = this.doctorService.findDoctorById(-1);
		Assertions.assertThat(doctor2).isNull();

	}


	@Test
	@Transactional
	void shouldDeleteDoctor() throws DataIntegrityViolationException, DataAccessException, DeleteDoctorException {

		Doctor d = this.doctorService.findDoctorById(7);
		Collection<Doctor> doctors1 = this.doctorService.findAllDoctors();
		Assertions.assertThat(doctors1).isNotNull().isNotEmpty();

		this.doctorService.delete(d);
		Collection<Doctor> doctors2 = this.doctorService.findAllDoctors();
		Assertions.assertThat(doctors2).isNotNull();

		Assertions.assertThat(doctors2).hasSize(doctors1.size() - 1);

	}

	@Test
	@Transactional
	void shouldNotDeleteDoctor() {

		Collection<Doctor> doctors1 = this.doctorService.findAllDoctors();
		Assertions.assertThat(doctors1).isNotNull().isNotEmpty();

		Assertions.assertThatThrownBy(() -> {
			Doctor d = this.doctorService.findDoctorById(1);
			this.doctorService.delete(d);
		}).isInstanceOf(DeleteDoctorException.class);

		Collection<Doctor> doctors2 = this.doctorService.findAllDoctors();
		Assertions.assertThat(doctors2).isNotNull();

		Assertions.assertThat(doctors2).hasSize(doctors1.size());

	}


}
