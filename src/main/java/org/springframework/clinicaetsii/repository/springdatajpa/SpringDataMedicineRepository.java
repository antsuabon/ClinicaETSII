package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.repository.MedicineRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataMedicineRepository extends MedicineRepository, CrudRepository<Medicine, Integer> {

}
