package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository {

	Collection<Doctor> findAll() throws DataAccessException;

	Collection<Doctor> findDoctorsSortedByNumOfServices() throws DataAccessException;

	Doctor findDoctorByUsername(String username) throws DataAccessException;

	Doctor findById(int id) throws DataAccessException;

	Doctor findDoctorById(int id) throws DataAccessException;

	Doctor findDoctorByPatientId(@Param("id") int id) throws DataAccessException;

	Doctor save(Doctor doctor) throws DataAccessException, DataIntegrityViolationException;

	Collection<Service> findAllServices() throws DataAccessException;

	void delete(Doctor d) throws DataAccessException;

	void deleteAll() throws DataAccessException;

	Collection<Doctor> findDoctorsWithServices() throws DataAccessException;
}
