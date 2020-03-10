package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.dao.DataAccessException;

public interface DoctorRepository {
	
	Collection<Doctor> findAllDoctors() throws DataAccessException;
	
	Collection<Integer> findAllDoctorsId();
	
	Doctor findDoctorById(int id);

}
