package org.springframework.clinicaetsii.repository.springdatajpa;


import org.springframework.clinicaetsii.model.Examination;

import org.springframework.clinicaetsii.repository.ExaminationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataExaminationRepository  extends ExaminationRepository, CrudRepository<Examination, Integer> {

	
	@Override
	@Query("select examination from Examination examination where examination.id =:id")
	Examination findExaminationById(@Param("id") int  id);
}
