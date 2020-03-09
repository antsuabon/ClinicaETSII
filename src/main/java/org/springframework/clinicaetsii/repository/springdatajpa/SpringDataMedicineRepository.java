
package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataMedicineRepository extends PrescriptionRepository, Repository<Prescription, Integer> {

	@Override
	@Autowired
	@Query("select pr from Prescription pr where exists (select p from Patient p where p.id =?1)")
	Collection<Prescription> listPrescriptionsByPatient(int patientId);
}
