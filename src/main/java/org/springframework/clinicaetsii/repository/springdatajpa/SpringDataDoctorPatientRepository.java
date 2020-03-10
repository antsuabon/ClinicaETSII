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

import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.DoctorPatientRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA specialization of the {@link DoctorPatientRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataDoctorPatientRepository extends DoctorPatientRepository, Repository<Patient, Integer> {

	@Override
	@Query("SELECT patient FROM Patient patient WHERE patient.generalPractitioner.id =:doctorId")
	Collection<Patient> listPatients(@Param("doctorId") int doctorId);

	@Override
	@Query("SELECT consultation FROM Consultation consultation WHERE consultation.appointment.patient.id =:patientId")
	Collection<Consultation> findConsultsByPatientId(@Param("patientId") int patientId);

	@Override
	@Query("SELECT user.id FROM User user WHERE user.username LIKE :username%")
	int findUserIdByUsername(@Param("username") String username);

	@Override
	@Query("SELECT consultation FROM Consultation consultation WHERE consultation.id =:consultationId")
	Consultation showConsultation(@Param("consultationId") int consultationId);

}
