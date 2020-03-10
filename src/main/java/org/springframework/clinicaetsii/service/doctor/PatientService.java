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

package org.springframework.clinicaetsii.service.doctor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.DoctorPatientRepository;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PatientService {

	private DoctorPatientRepository	doctorPatientRepository;

	@Autowired
	private AuthoritiesService		authoritiesService;


	@Autowired
	public PatientService(final DoctorPatientRepository doctorPatientRepository) {
		this.doctorPatientRepository = doctorPatientRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Patient> listPatients(final int doctorId) throws DataAccessException {
		return this.doctorPatientRepository.listPatients(doctorId);
	}

	@Transactional(readOnly = true)
	public int findUserIdByUsername(final String username) throws DataAccessException {
		return this.doctorPatientRepository.findUserIdByUsername(username);
	}

	@Transactional(readOnly = true)
	public Collection<Consultation> findConsultsByPatientId(final int patientId) throws DataAccessException {
		return this.doctorPatientRepository.findConsultsByPatientId(patientId);
	}

	@Transactional(readOnly = true)
	public Consultation showConsultation(final int consultationId) throws DataAccessException {
		return this.doctorPatientRepository.showConsultation(consultationId);
	}

}