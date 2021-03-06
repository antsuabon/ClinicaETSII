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
	Collection<Prescription> findPrescriptionsByPatientUsername(@Param("username") String username);

	@Override
	@Query("select pr from Prescription pr where pr.patient.id =:patientId order by pr.startDate asc")
	Collection<Prescription> findPrescriptionsByPatientIdOrdered(@Param("patientId") int patientId);

	@Override
	@Query("select p from Prescription p where p.doctor.id =:doctorId")
	Collection<Prescription> findPrescriptionByDoctorId(@Param("doctorId")int doctorId);

	@Override
	@Query("select p from Prescription p where p.medicine.id =:medicineId")
	Prescription findPrescriptionByMedicineId(@Param("medicineId")int medicineId);
}