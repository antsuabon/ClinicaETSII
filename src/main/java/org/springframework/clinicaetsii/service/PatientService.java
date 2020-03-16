
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.DoctorRepository;
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
	public Patient findPatientById(final int id) throws DataAccessException {
		return this.patientRepository.findAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
	}

	@Transactional(readOnly = true)
	public Patient findPatientByUsername() throws DataAccessException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		return this.patientRepository.findByUsername(user.getUsername());
	}

	@Transactional(readOnly = true)
	public Doctor findDoctorByPatient(final int id) throws DataAccessException {
		return this.doctorRepository.findDoctorByPatientId(id);
	}

	@Transactional(readOnly = true)
	public Patient findPatient(final int id) {
		return this.patientRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Patient> findAllPatientFromDoctors(final int id) {
		return this.patientRepository.findDoctorPatients(id);
	}

	@Transactional
	public void savePatient(final Patient patient) {
		this.patientRepository.save(patient);
	}

	@PreAuthorize("hasAuthority('patient')")
	public Patient findCurrentPatient() throws DataAccessException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();

		return this.patientRepository.findByUsername(username);
	}
}
