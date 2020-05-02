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

package org.springframework.clinicaetsii.repository;

import java.util.Collection;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface PatientRepository {

	Collection<Patient> findAll() throws DataAccessException;

	Patient save(Patient patient) throws DataAccessException;

	void delete(Patient patient) throws DataAccessException;

	Patient findById(int id) throws DataAccessException;

	Patient findPatientByUsername(@Param("username") String username) throws DataAccessException;

	User findAdministrativeByUsername(
			@Param("username") String username) throws DataAccessException;

	Collection<Patient> findDoctorPatients(int id) throws DataAccessException;

	Collection<Patient> findPatientsByDoctorUsername(
			@Param("doctorUsername") String doctorUsername) throws DataAccessException;

	Collection<Appointment> findAppointmentsByPatientUsername(
			@Param("patientUsername") String patientUsername) throws DataAccessException;

	Collection<Appointment> findAppointmentsByPatientUsernameDone(
			@Param("patientUsername") String username) throws DataAccessException;

	Collection<Appointment> findAppointmentsByPatientUsernameDelete(
			@Param("patientUsername") String patientUsername) throws DataAccessException;


	void deleteAll() throws DataAccessException;
}
