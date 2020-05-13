package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Administrative;
import org.springframework.dao.DataAccessException;

public interface AdministrativeRepository {
	
	Collection<Administrative> findAll() throws DataAccessException;
	
	Administrative findById(int id) throws DataAccessException;
	
	void delete(Administrative administrative) throws DataAccessException;

}
