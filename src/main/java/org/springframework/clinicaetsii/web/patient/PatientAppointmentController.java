
package org.springframework.clinicaetsii.web.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/patient/appointments")
public class PatientAppointmentController {

	private final AppointmentService		appointmentService;
	private final DoctorService				doctorService;
	private final PatientService			patientService;

	@Autowired
	public PatientAppointmentController(final AppointmentService appointmentService, final DoctorService doctorService, final PatientService patientService) {
		this.appointmentService = appointmentService;
		this.doctorService = doctorService;
		this.patientService = patientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");

	}

	@GetMapping(value = "/table")
	public String generateTable(final Map<String, Object> model) {
		List<LocalDateTime> citas = new ArrayList<>(this.appointmentService.findAppointmentByDoctors(this.patientService.findCurrentPatient().getGeneralPractitioner().getId()));
		List<LocalDateTime> table = this.timeTable(LocalDate.now());
		table.removeAll(citas);
		if (table.isEmpty()) {
			model.put("emptylist", true);
		} else {
			model.put("hours", table);

		}
		return "/patient/appointments/timeTable";
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final Map<String, Object> model, @RequestParam("fecha") final LocalDateTime fecha) {
		Appointment appointment = new Appointment();
		appointment.setPatient(this.patientService.findCurrentPatient());

		appointment.setStartTime(fecha);
		appointment.setEndTime(fecha.plusMinutes(7));

		model.put("appointment", appointment);

		return "/patient/appointments/requestAppointment";

	}

	@PostMapping(value = "/save")
	public String processCreationForm(@Valid final Appointment appointment,final BindingResult result) {
		Patient p = this.patientService.findPatientByUsername();
		if (result.hasErrors()) {
			return "redirect:/patient/appointments/new";
		} else {

			appointment.setPatient(p);
			appointment.setPriority(false);

			this.appointmentService.saveAppointment(appointment);
			return "redirect:/patient/appointments/table";
		}

	}

	public List<LocalDateTime> timeTable(final LocalDate l) {

		List<LocalDateTime> hours = new ArrayList<>();
		LocalDateTime start = LocalDateTime.of(l.getYear(), l.getMonthValue(), l.getDayOfMonth(), 9, 0);
		hours.add(start);
		LocalDateTime end = start.plusHours(1);
		while (start.isBefore(end)) {
			start = start.plusMinutes(7);
			hours.add(start);
		}
		hours.add(end);

		return hours;
	}
}