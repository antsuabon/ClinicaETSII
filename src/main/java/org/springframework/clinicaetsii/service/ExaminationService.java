package org.springframework.clinicaetsii.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExaminationService {

	
	private ExaminationRepository examinationRepository;

	@Autowired
	public ExaminationService(final ExaminationRepository examinationRepository) {
		this.examinationRepository = examinationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Examination> findAllDoctors() throws DataAccessException {
		return this.examinationRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Examination findExaminationsById(int id) throws DataAccessException {
		return this.examinationRepository.findExaminationById(id);
	}

	public void saveExamination(@Valid Examination examination) {
		this.examinationRepository.save(examination);
		
	}
	
}
