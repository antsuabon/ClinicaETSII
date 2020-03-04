package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

	private AppointmentRepository appointmentRepository;

	@Autowired
	public AppointmentService(final AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Transactional
	public Collection<Appointment> findAllAppointmentsByDoctorId(final int id) {
		return this.appointmentRepository.findAllByDoctorId(id);
	}

	@Transactional
	public Appointment findOneById(final int id) {
		return this.appointmentRepository.findOneById(id);
	}

}
