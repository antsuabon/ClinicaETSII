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

package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataPatientRepository extends PatientRepository, CrudRepository<Patient, Integer> {

	@Override
	@Query("SELECT patient FROM Patient patient WHERE patient.generalPractitioner.username LIKE :doctorUsername")
	Collection<Patient> findPatientsByDoctorUsername(@Param("doctorUsername") String doctorUsername);

	@Override
	@Query("SELECT appointment FROM Appointment appointment WHERE appointment.patient.username LIKE :patientUsername")
	Collection<Appointment> findAppointmentsByPatientUsername(@Param("patientUsername") String patientUsername);

}
