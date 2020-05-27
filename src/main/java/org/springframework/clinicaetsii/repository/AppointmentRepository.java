
package org.springframework.clinicaetsii.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.projection.AppointmentPatient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository {

	Appointment findById(@Param("appointmentId") int appointmentId) throws DataAccessException;

	Collection<Appointment> findAll() throws DataAccessException;

	Collection<Appointment> findAppointmentsWithoutConsultationByDoctorUsername(
			@Param("doctorUsername") String doctorUsername) throws DataAccessException;

	Collection<AppointmentPatient> findAppointmentsPatientsWithoutConsultationByDoctorUsername(
			@Param("doctorUsername") String doctorUsername);

	Collection<LocalDateTime> findAppointmentsDatesByDoctorId(
			@Param("id") int id) throws DataAccessException;

	Appointment save(Appointment appointment) throws DataAccessException;

	void delete(Appointment appointment) throws DataAccessException;

	void deleteAll() throws DataAccessException;

}
