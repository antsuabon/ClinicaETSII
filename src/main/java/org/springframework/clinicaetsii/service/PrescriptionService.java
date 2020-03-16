package org.springframework.clinicaetsii.service;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrescriptionService {

	
	private PrescriptionRepository prescriptionRepository;
	
	
	@Autowired
	public PrescriptionService(final PrescriptionRepository prescriptionRepository) {
		this.prescriptionRepository = prescriptionRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Prescription> findAllPrescriptions() throws DataAccessException {
		return this.prescriptionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public void savePrescription(Prescription prescription) throws DataAccessException {
		 this.prescriptionRepository.save(prescription);
	}
}
