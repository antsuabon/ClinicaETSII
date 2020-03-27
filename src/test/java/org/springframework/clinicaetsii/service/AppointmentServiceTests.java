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

import java.time.LocalDateTime;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AppointmentServiceTests {

	@Autowired
	protected AppointmentService appointmentService;
	@Autowired
	protected PatientService patientService;


	@Test
	void shouldFindAppointmentsByDoctorId() {
		Collection<LocalDateTime> fechasInicioAppointments1 =
				this.appointmentService.findAppointmentByDoctors(1);
		Assertions.assertThat(fechasInicioAppointments1.size()).isEqualTo(3);

		Collection<LocalDateTime> fechasInicioAppointments2 =
				this.appointmentService.findAppointmentByDoctors(-1);
		Assertions.assertThat(fechasInicioAppointments2).isEmpty();

	}

	@Test
	@Transactional
	public void shouldInsertAppointment() {
		Collection<Appointment> appointments1 = this.appointmentService.findAll();
		int tamañoInicial = appointments1.size();

		Patient patient1 = this.patientService.findPatientById(4);

		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fin = inicio.plusMinutes(7);

		Appointment appointment1 = new Appointment();
		appointment1.setPatient(patient1);
		appointment1.setPriority(false);
		appointment1.setEndTime(inicio);
		appointment1.setStartTime(fin);

		this.appointmentService.saveAppointment(appointment1);
		Assertions.assertThat(appointment1.getId().longValue()).isNotEqualTo(0);

		Collection<Appointment> appointments2 = this.appointmentService.findAll();
		int tamañoFinal = appointments2.size();
		Assertions.assertThat(tamañoFinal).isEqualTo(tamañoInicial + 1);
	}

}
