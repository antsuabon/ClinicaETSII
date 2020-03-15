package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.dao.DataAccessException;

public interface PrescriptionRepository {
	
	Collection<Prescription> listPrescriptionsByPatient(String username) throws DataAccessException;
	
	Prescription findById(int id) throws DataAccessException;

}
