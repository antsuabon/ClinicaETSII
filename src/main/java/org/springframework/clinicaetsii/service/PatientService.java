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

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

	private PatientRepository	patientRepository;
	private DoctorRepository	doctorRepository;

	@Autowired
	public PatientService(final PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Patient> findPatients() throws DataAccessException {
		return this.patientRepository.findAll();
	}

	@PreAuthorize("hasAuthority('doctor')")
	public Collection<Patient> findCurrentDoctorPatients() throws DataAccessException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();

		return this.patientRepository.findPatientsByDoctorUsername(username);
	}
	
  @Transactional(readOnly = true)
	public Patient findPatientById(int id) throws DataAccessException{
		return this.patientRepository.findAll().stream().filter(x->x.getId() == id).findFirst().orElse(null);
	}

  @Transactional(readOnly = true)
	public Patient findPatientByUsername() throws DataAccessException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		return this.patientRepository.findByUserName(user.getUsername());
	}
  
  @Transactional(readOnly = true)
	public Doctor findDoctorByPatient(final int id) throws DataAccessException {
		return this.patientRepository.findDoctorByPatient(id);
	}

}
