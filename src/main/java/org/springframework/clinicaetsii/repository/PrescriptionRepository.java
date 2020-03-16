package org.springframework.clinicaetsii.repository;

import java.util.List;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.dao.DataAccessException;


public interface PrescriptionRepository{

	Collection<Prescription> listPrescriptionsByPatient(String username) throws DataAccessException;
  
	List<Prescription> findAll() throws DataAccessException; 
  
  Prescription findById(int id) throws DataAccessException;
	
	void save(Prescription prescription) throws DataAccessException;
  
  void delete(Prescription prescription) throws DataAccessException;
	
}
