
package org.springframework.clinicaetsii.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.dao.DataAccessException;

public interface PrescriptionRepository {

	Collection<Prescription> findPrescriptionsByPatientUsername(String username) throws DataAccessException;

	Collection<Prescription> findPrescriptionsByPatientId(int patientId) throws DataAccessException;

	List<Prescription> findAll() throws DataAccessException;

	Prescription findById(int id) throws DataAccessException;

	Prescription save(Prescription prescription) throws DataAccessException;

	void delete(Prescription prescription) throws DataAccessException;

}
