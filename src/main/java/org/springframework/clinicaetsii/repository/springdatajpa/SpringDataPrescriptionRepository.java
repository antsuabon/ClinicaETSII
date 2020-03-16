package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataPrescriptionRepository extends PrescriptionRepository, CrudRepository<Prescription, Integer> {

	@Override
	@Query("select pr from Prescription pr where pr.patient.username =:username")
	Collection<Prescription> listPrescriptionsByPatient(@Param("username")String username);
	
	@Override
	@Query("select pr from Prescription pr where pr.id =:id")
	Prescription findById(@Param("id") int id);
}