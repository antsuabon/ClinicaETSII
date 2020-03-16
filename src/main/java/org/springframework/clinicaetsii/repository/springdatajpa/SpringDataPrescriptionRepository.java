package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataPrescriptionRepository extends PrescriptionRepository, CrudRepository<Prescription, Integer>{

}
