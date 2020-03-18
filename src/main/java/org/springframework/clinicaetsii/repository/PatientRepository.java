package org.springframework.clinicaetsii.repository;

import javax.validation.Valid;

import org.springframework.clinicaetsii.model.Patient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface PatientRepository  {

	Patient findPatient(@Param("username") String username) throws DataAccessException;

	Patient save(@Valid Patient patient) throws DataAccessException;

	Patient findPatientByUsername(@Param("username") String username) throws DataAccessException;

}
