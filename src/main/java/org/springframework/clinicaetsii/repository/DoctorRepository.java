package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository {

	Collection<Doctor> findAll() throws DataAccessException;

	Collection<Doctor> findDoctorsSortedByNumOfServices() throws DataAccessException;

	Doctor findDoctorByUsername(String username) throws DataAccessException;

	Doctor findById(int id) throws DataAccessException;

	Collection<Integer> findAllDoctorsId() throws DataAccessException;

	Doctor findDoctorById(int id) throws DataAccessException;

	Doctor findDoctorByPatientId(@Param("id") int id);

}
