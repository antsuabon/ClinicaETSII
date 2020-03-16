package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.repository.MedicineRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataMedicineRepository extends MedicineRepository, CrudRepository<Medicine, Integer> {
	
	@Override
	@Query("SELECT m FROM Medicine m WHERE m.id =:id")
	Medicine findMedicineById(@Param("id" )int id);

}
