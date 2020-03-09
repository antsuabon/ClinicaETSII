package org.springframework.clinicaetsii.repository.springdatajpa;


import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataPatientRepository extends PatientRepository, CrudRepository<Patient, Integer>{

	@Override
	@Query("SELECT patient FROM Patient patient WHERE patient.id = :id")
	public Patient findById(@Param("id") int id);

}
