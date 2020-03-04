package org.springframework.clinicaetsii.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppointmentController {

	private AppointmentService appointmentService;

	@Autowired
	public AppointmentController(final AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}


	@GetMapping("/doctors/{doctorId}/appointments")
	public String processListByDoctor(@PathVariable("doctorId") final int doctorId, final Map<String, Object> model) {

		Collection<Appointment> results = this.appointmentService.findAllAppointmentsByDoctorId(doctorId);
		model.put("appointments", results);

		return "appointments/appointmentsListByDoctor";
	}

}
