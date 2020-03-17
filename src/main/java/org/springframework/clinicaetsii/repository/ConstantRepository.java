package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.dao.DataAccessException;

public interface ConstantRepository {

	Constant save(Constant constant) throws DataAccessException;

	Collection<ConstantType> findAllConstantTypes() throws DataAccessException;

}
