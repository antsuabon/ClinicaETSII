package org.springframework.clinicaetsii.repository;

import java.util.List;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.dao.DataAccessException;

public interface PrescriptionRepository{

	
	List<Prescription> findAll() throws DataAccessException; 
	
	void save(Prescription prescription) throws DataAccessException;
	
}
