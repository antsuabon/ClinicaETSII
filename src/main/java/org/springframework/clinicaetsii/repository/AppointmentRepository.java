package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.dao.DataAccessException;

public interface AppointmentRepository {

	Collection<Appointment> findAllByDoctorId(int id) throws DataAccessException;

	Appointment findOneById(int id) throws DataAccessException;

}
