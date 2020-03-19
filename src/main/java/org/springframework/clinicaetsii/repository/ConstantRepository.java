package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.dao.DataAccessException;

public interface ConstantRepository {

	Constant findById(int constantId) throws DataAccessException;

	Constant save(Constant constant) throws DataAccessException;

	void delete(Constant constant) throws DataAccessException;

	Collection<ConstantType> findAllConstantTypes() throws DataAccessException;

}
