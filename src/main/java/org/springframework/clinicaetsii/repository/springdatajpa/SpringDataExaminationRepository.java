package org.springframework.clinicaetsii.repository.springdatajpa;


import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataExaminationRepository  extends ExaminationRepository, CrudRepository<Examination, Integer> {

}
