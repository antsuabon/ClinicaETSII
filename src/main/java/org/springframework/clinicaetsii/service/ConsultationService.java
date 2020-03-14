package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultationService {

	private ConsultationRepository	consultationRepository;

	@Autowired
	public ConsultationService(final ConsultationRepository	consultationRepository) {
		this.consultationRepository = consultationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Consultation> findConsultationsByPatientId(final int patientId) throws DataAccessException {
		return this.consultationRepository.findConsultationsByPatientId(patientId);
	}

	@Transactional(readOnly = true)
	public Consultation findConsultationById(final int consultationId) throws DataAccessException {
		return this.consultationRepository.findById(consultationId);
	}

	@Transactional
	public void save(final Consultation consultation) throws DataAccessException {
		this.consultationRepository.save(consultation);
	}

	@Transactional(readOnly = true)
	public Collection<DischargeType> findDischargeTypes() throws DataAccessException {
		return this.consultationRepository.findDischargeTypes();
	}
}
