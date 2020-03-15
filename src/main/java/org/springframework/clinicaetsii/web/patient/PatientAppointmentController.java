package org.springframework.clinicaetsii.web.patient;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient/appointments")
public class PatientAppointmentController {

	private PatientService patientService;

	@Autowired
	public PatientAppointmentController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public String listAppointmentsPatient(final Map<String, Object> model) {

		Collection<Appointment> results = this.patientService.findAppointments();

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("appointments", results);
		}

		return "/patient/appointments/appointmentsList";
	}
}
