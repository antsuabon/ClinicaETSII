package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataAppointmentRepository extends AppointmentRepository, CrudRepository<Appointment, Integer> {

	@Override
	@Query("SELECT appointment FROM Appointment appointment WHERE appointment.patient.generalPractitioner.id = :id")
	public Collection<Appointment> findAllByDoctorId(@Param("id") int id);

	@Override
	@Query("SELECT appointment FROM Appointment appointment WHERE appointment.id = :id")
	public Appointment findOneById(@Param("id") int id);
}
