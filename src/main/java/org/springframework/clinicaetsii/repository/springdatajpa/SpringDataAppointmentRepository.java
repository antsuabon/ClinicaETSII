
package org.springframework.clinicaetsii.repository.springdatajpa;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataAppointmentRepository extends AppointmentRepository, CrudRepository<Appointment, Integer> {

	@Override
	@Query("SELECT appointment FROM Appointment appointment WHERE (appointment.patient.generalPractitioner.username LIKE :doctorUsername) AND NOT EXISTS (SELECT consultation FROM Consultation consultation WHERE consultation.appointment = appointment) ORDER BY appointment.priority, appointment.startTime DESC")
	Collection<Appointment> findAppointmentsWithoutConsultationByDoctorUsername(@Param("doctorUsername") String doctorUsername);

	@Override
	@Query("SELECT appointment.startTime FROM Appointment appointment WHERE appointment.patient.generalPractitioner.id =:id")
	Collection<LocalDateTime> findAppointmentsDatesByDoctorId(@Param("id") int id);

	@Override
	@Query("SELECT appointment FROM Appointment appointment WHERE appointment.patient.generalPractitioner.id =:id")
	Collection<Appointment> findAppointmentsByDoctorId(@Param("id") int id);

}
