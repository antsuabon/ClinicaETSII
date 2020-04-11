package org.springframework.clinicaetsii.repository.springdatajpa;


import java.util.Collection;

import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataExaminationRepository  extends ExaminationRepository, CrudRepository<Examination, Integer> {
	
	@Override
	@Query("select e.examinations from Consultation e where e.id =:id")
	Collection<Examination> findAllSorted(int id);
	
}
