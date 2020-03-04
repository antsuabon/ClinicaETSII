package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataConsultationRepository extends ConsultationRepository, CrudRepository<Consultation, Integer> {

}
