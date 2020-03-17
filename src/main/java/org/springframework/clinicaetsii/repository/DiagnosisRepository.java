package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.dao.DataAccessException;

public interface DiagnosisRepository {

	Diagnosis save(Diagnosis diagnosis) throws DataAccessException;

	Collection<Diagnosis> findAll() throws DataAccessException;

}
