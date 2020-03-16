package org.springframework.clinicaetsii.repository;

import java.util.Collection; 

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.dao.DataAccessException;

public interface DoctorRepository {

	Collection<Doctor> findAll() throws DataAccessException;
   
	Collection<Doctor> findDoctorsSortedByNumOfServices() throws DataAccessException;
	
	Doctor findDoctorByUsername(String username) throws DataAccessException;
  
  Doctor findById(@Param("id") int id);
  
  Collection<Integer> findAllDoctorsId();
  
  Doctor findDoctorById(int id);

}
