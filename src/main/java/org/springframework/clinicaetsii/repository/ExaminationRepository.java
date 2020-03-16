
package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Examination;
import org.springframework.dao.DataAccessException;

public interface ExaminationRepository {

	Collection<Examination> findAll() throws DataAccessException;

	Examination findById(int id) throws DataAccessException;

	Examination save(Examination examination) throws DataAccessException;

}
