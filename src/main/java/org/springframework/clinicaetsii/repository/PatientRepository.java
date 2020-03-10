
package org.springframework.clinicaetsii.repository;

import java.util.Collection; 

import org.springframework.clinicaetsii.model.Patient;
import org.springframework.dao.DataAccessException;


public interface PatientRepository {

	Patient findById(int id) throws DataAccessException;

	Collection<Patient> findDoctorPatients(int id) throws DataAccessException;
	
	void save(Patient patient);

	//void updatePatient(int id) throws DataAccessException;
}
