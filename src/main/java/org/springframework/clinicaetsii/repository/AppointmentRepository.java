
package org.springframework.clinicaetsii.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository {

	Collection<Appointment> findAll() throws DataAccessException;

	Appointment findById(int appointmentId) throws DataAccessException;

	Collection<Appointment> findAppointmentsWithoutConsultationByDoctorUsername(@Param("doctorUsername") String doctorUsername) throws DataAccessException;

	Appointment save(Appointment appointment) throws DataAccessException;

	Collection<LocalDateTime> findAppointmentByDoctors(@Param("id") int id);

}
