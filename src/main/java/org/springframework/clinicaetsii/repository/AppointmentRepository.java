package org.springframework.clinicaetsii.repository;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository {

	Appointment findById(@Param("appointmentId")int appointmentId) throws DataAccessException;

	void delete(Appointment appointment) throws DataAccessException;

	Collection<Appointment> findAppointmentsWithoutConsultationByDoctorUsername(@Param("doctorUsername") String doctorUsername) throws DataAccessException;

	Appointment save(Appointment appointment) throws DataAccessException;

	Collection<LocalDateTime> findAppointmentByDoctors(@Param("id") int id) throws DataAccessException;

}
