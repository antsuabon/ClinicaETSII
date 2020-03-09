package org.springframework.clinicaetsii.repository;

import org.springframework.clinicaetsii.model.Patient;
import org.springframework.dao.DataAccessException;

public interface PatientRepository {

	Patient findById(int id) throws DataAccessException;

	void save(Patient patient) throws DataAccessException;
}
