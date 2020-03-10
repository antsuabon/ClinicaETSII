
package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataPatientRepository extends PatientRepository, CrudRepository<Patient, Integer> {

	@Override
	@Query("SELECT p FROM Patient p WHERE (p.id =:id)")
	Patient findById(@Param("id") int id);

	@Override
	@Query("select p from Patient p where exists (select d from Doctor d where d.id =:id)")
	Collection<Patient> findDoctorPatients(@Param("id") int id);
	
	/*@Override
	@Query("UPDATE Patient p SET p.generalPractitioner =:doctor")
	void updatePatient(@Param("id") int id ); */

}
