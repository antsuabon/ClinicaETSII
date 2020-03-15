package org.springframework.clinicaetsii.service;

import javax.validation.Valid;

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

	private PatientRepository patientRepository;

	@Autowired
	public PatientService(final PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('patient')")
	public Patient findPatient() throws DataAccessException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();

		return this.patientRepository.findPatient(username);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('patient')")
	public void save(@Valid final Patient patient) {

		this.patientRepository.save(patient);

	}

}
