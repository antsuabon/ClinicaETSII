package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	private AppointmentRepository appointmentRepository;


	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Transactional(readOnly = true)
	public Appointment findAppointmentById(final int appointmentId) throws DataAccessException {
		return this.appointmentRepository.findById(appointmentId);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('doctor')")
	public Collection<Appointment> findCurrentDoctorAppointments() throws DataAccessException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();

		return this.appointmentRepository.findAppointmentsByDoctorUsername(username);
	}


}
