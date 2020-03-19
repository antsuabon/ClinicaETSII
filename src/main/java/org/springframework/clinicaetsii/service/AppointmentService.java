package org.springframework.clinicaetsii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	private AppointmentRepository	appointmentRepository;

	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('patient')")
	public Appointment findById(final int appointmentId) {
		return this.appointmentRepository.findById(appointmentId);
	}

	@Transactional
	@PreAuthorize("hasAuthority('patient')")
	public void deleteAppointment(final Appointment appointment) {
		this.appointmentRepository.delete(appointment);

	}


}
