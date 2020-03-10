package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataDoctorRepository extends DoctorRepository, CrudRepository<Doctor, Integer> {
	
	@Override
	@Query("SELECT d FROM Doctor d")
	Collection<Doctor> findAllDoctors();

	@Override
	@Query("SELECT d.id FROM Doctor d")
	Collection<Integer> findAllDoctorsId();
	
	@Override
	@Query("SELECT d from Doctor d where d.id =:id")
	Doctor findDoctorById(@Param("id") int id);

}
