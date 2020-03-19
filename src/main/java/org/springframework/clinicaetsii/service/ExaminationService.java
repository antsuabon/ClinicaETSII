package org.springframework.clinicaetsii.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.comparator.Comparators;

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
	public Examination findExaminationsById(final int id) throws DataAccessException {
		return this.examinationRepository.findById(id);
	}
	

	@Transactional(readOnly = true)
	public List<Examination> findExaminationsSortedByStartDate(int id) throws DataAccessException {
		return this.examinationRepository.findAllSorted(id).stream().sorted(Comparator.comparing(c ->c.getStartTime())).collect(Collectors.toList());
	}

	@Transactional
	public void saveExamination(final Examination examination) {
		this.examinationRepository.save(examination);

	}
	

}
