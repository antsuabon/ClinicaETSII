package org.springframework.clinicaetsii.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public AppointmentService(final AppointmentRepository AppointmentRepository) {
		this.appointmentRepository = AppointmentRepository;
	}

	@Transactional(readOnly = true)
	public Collection<LocalDateTime> findAppointmentByDoctors(final int id) throws DataAccessException {
		return this.appointmentRepository.findAppointmentByDoctors(id);
	}

	@Transactional
	public void saveAppointment(final Appointment appointment) throws DataAccessException {
		this.appointmentRepository.save(appointment);

	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('patient')")
	public Appointment findAppointmentById(final int appointmentId) throws DataAccessException {
		return this.appointmentRepository.findById(appointmentId);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('doctor')")
	public Collection<Appointment> findCurrentDoctorAppointments() throws DataAccessException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();

		return this.appointmentRepository.findAppointmentsWithoutConsultationByDoctorUsername(username);
	}

	@Transactional
	@PreAuthorize("hasAuthority('patient')")
	public void deleteAppointment(final Appointment appointment) {
		this.appointmentRepository.delete(appointment);
	}

}
