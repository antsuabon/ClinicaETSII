package org.springframework.clinicaetsii.web.patient;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient/appointments")
public class PatientAppointmentController {

	private PatientService patientService;

	private AppointmentService appointmentService;

	@Autowired
	public PatientAppointmentController(final PatientService patientService, final AppointmentService appointmentService) {
		this.patientService = patientService;
		this.appointmentService = appointmentService;
	}

	@GetMapping
	public String listAppointmentsPatient(final Map<String, Object> model) {

		Collection<Appointment> appointmentsDelete = this.patientService.findAppointmentsDelete();
		Collection<Appointment> appointmentsDone = this.patientService.findAppointmentsDone();

		if (appointmentsDelete.isEmpty()) {
			model.put("emptyListDelete", true);
		} else {
			model.put("appointmentsDelete", appointmentsDelete);
		}

		if (appointmentsDone.isEmpty()) {
			model.put("emptyListDone", true);
		} else {
			model.put("appointments", appointmentsDone);
		}

		return "/patient/appointments/appointmentsList";
	}

	@GetMapping("/{appointmentId}/delete")
	public String deleteAppointent(@PathVariable("appointmentId") final int appointmentId) {

		Appointment appointment = this.appointmentService.findById(appointmentId);

		if(appointment !=null) {
		this.appointmentService.deleteAppointment(appointment);
		}

		return "redirect:/patient/appointments";
	}
}
