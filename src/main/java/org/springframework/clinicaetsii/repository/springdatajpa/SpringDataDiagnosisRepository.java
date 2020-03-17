package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.clinicaetsii.repository.DiagnosisRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataDiagnosisRepository extends DiagnosisRepository, CrudRepository<Diagnosis, Integer> {

}
