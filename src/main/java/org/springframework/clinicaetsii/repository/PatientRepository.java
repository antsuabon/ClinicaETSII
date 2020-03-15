package org.springframework.clinicaetsii.repository;

import javax.validation.Valid;

import org.springframework.clinicaetsii.model.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface PatientRepository {

	Patient findPatient(@Param("username") String username) throws DataAccessException;

	void save(@Valid Patient patient) throws DataAccessException;

}
