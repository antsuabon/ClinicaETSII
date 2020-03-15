package org.springframework.clinicaetsii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicineService {
	
	private MedicineRepository medicineRepository;
	
	@Autowired
	public MedicineService(final MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}
	
	@Transactional(readOnly = true)
	public Medicine findMedicineById(int id) {
		return this.medicineRepository.findMedicineById(id);
	}

}
