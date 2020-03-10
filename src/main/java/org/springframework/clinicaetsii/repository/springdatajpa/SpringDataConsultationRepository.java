package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataConsultationRepository extends ConsultationRepository,CrudRepository<Consultation, Integer> {

	@Override
	@Query("SELECT consultation FROM Consultation consultation WHERE consultation.appointment.patient.id =:patientId")
	Collection<Consultation> findConsultationsByPatientId(@Param("patientId") int patientId);

}
