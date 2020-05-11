
package org.springframework.clinicaetsii.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.dao.DataAccessException;

public interface PrescriptionRepository {

	Collection<Prescription> findPrescriptionsByPatientUsername(
			String username) throws DataAccessException;

	Collection<Prescription> findPrescriptionsByPatientIdOrdered(
			int patientId) throws DataAccessException;

	List<Prescription> findAll() throws DataAccessException;

	Prescription findById(int id) throws DataAccessException;

	Prescription save(Prescription prescription) throws DataAccessException;

	void delete(Prescription prescription) throws DataAccessException;

	Collection<Prescription> findPrescriptionByDoctorId(int doctorId) throws DataAccessException;

	void deleteAll() throws DataAccessException;

	Prescription findPrescriptionByMedicineId(int medicineId) throws DataAccessException;

}
