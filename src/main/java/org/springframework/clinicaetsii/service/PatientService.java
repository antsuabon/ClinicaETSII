package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

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

  
  public Patient findPatient(int id) {
		return this.patientRepository.findById(id);
	}
	
	public Collection<Patient> findAllPatientFromDoctors(int id) {
		return this.patientRepository.findDoctorPatients(id);
	}
	
	public void savePatient(Patient patient) {
		this.patientRepository.save(patient);
  }
}
