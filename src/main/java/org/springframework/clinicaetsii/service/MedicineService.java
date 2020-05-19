
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.repository.MedicineRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicineService {

	private MedicineRepository		medicineRepository;



	@Autowired
	public MedicineService(final MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Medicine> findAllMedicines() throws DataAccessException {
		return this.medicineRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Medicine findMedicineById(final int id) throws DataAccessException {
		return this.medicineRepository.findById(id);
	}

	@Transactional
	public void saveMedicine(final Medicine medicine) throws DataAccessException {
		this.medicineRepository.save(medicine);
	}

	@Transactional
	public void deleteMedicine(final Medicine medicine) throws DataAccessException {
		this.medicineRepository.delete(medicine);

	}
}
