package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository {

	Appointment findById(int appointmentId) throws DataAccessException;

	Collection<Appointment> findAppointmentsWithoutConsultationByDoctorUsername(@Param("doctorUsername") String doctorUsername) throws DataAccessException;

}
