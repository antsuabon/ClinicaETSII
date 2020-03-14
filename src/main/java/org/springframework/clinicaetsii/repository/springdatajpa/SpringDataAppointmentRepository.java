package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataAppointmentRepository extends AppointmentRepository, CrudRepository<Appointment, Integer> {

	@Override
	@Query("SELECT appointment FROM Appointment appointment WHERE appointment.patient.generalPractitioner.username LIKE :doctorUsername ORDER BY appointment.priority, appointment.startTime DESC")
	Collection<Appointment> findAppointmentsByDoctorUsername(@Param("doctorUsername") String doctorUsername);

}
