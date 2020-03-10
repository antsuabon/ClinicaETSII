package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface ConsultationRepository {

	Consultation findById(int consultationId) throws DataAccessException;

	Collection<Consultation> findConsultationsByPatientId(@Param("patientId") int patientId) throws DataAccessException;

}
