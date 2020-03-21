package org.springframework.clinicaetsii.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppointmentController {

	private final AppointmentService		appointmentService;
	private final DoctorService				doctorService;
	private final PatientService			patientService;

	@Autowired
	public AppointmentController(final AppointmentService appointmentService, final DoctorService doctorService, final PatientService patientService) {
		this.appointmentService = appointmentService;
		this.doctorService = doctorService;
		this.patientService = patientService;
	}


	@GetMapping("/doctors/{doctorId}/appointments")
	public String processListByDoctor(@PathVariable("doctorId") final int doctorId, final Map<String, Object> model) {

		Collection<Appointment> results = this.appointmentService.findAllAppointmentsByDoctorId(doctorId);
		model.put("appointments", results);

		return "appointments/appointmentsListByDoctor";
	}

}
