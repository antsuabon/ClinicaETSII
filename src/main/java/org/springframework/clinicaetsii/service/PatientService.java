package org.springframework.clinicaetsii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

	private PatientRepository patientRepository;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public PatientService(final PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional
	public Patient findById(final int id) {
		return this.patientRepository.findById(id);
	}

	@Transactional
	public void save(final Patient patient) throws DataAccessException {
		this.patientRepository.save(patient);
		this.authoritiesService.saveAuthorities(patient.getUsername(), "patient");
	}
}
