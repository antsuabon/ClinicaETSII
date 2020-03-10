
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

	private PatientRepository patientRepository;


	@Autowired
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
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
