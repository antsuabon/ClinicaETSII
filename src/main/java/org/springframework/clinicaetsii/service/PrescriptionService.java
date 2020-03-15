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
	public Collection<Prescription> findPrescriptionsFromPatient(String username) throws DataAccessException {
		return this.prescriptionRepository.listPrescriptionsByPatient(username);
	}
	
	@Transactional(readOnly = true)
	public Prescription findPrescriptionById(int prescriptionId) throws DataAccessException {
		return this.prescriptionRepository.findById(prescriptionId);
	}

}
