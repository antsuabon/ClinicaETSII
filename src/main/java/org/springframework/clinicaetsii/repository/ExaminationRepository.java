
package org.springframework.clinicaetsii.repository;

import java.util.Collection;



import org.springframework.clinicaetsii.model.Examination;
import org.springframework.dao.DataAccessException;


public interface ExaminationRepository {

	
	Collection<Examination> findAll() throws DataAccessException;
	Examination findExaminationById(int  id) throws DataAccessException;
	void save(Examination examination);
	
}
