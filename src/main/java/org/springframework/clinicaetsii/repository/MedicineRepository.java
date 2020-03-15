package org.springframework.clinicaetsii.repository;

import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.dao.DataAccessException;

public interface MedicineRepository {
	
	Medicine findMedicineById(int id) throws DataAccessException;

}
