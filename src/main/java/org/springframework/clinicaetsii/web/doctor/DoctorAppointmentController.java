package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor/appointments")
public class DoctorAppointmentController {

	private AppointmentService appointmentService;

	@Autowired
	public DoctorAppointmentController(final AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	@GetMapping
	public String listAppointments(final Map<String, Object> model) {
		Collection<Appointment> results = this.appointmentService.findCurrentDoctorAppointments();

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("appointments", results);
		}

		return "/doctor/consultations/appointmentsList";
	}

}
