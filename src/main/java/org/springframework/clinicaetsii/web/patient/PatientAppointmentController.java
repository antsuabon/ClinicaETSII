
package org.springframework.clinicaetsii.web.patient;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.clinicaetsii.web.validator.AppointmentValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class PatientAppointmentController {

	private final AppointmentService appointmentService;
	private final PatientService patientService;
	private static final LocalDateTimeFormatter FORMATTER = new LocalDateTimeFormatter();


	@Autowired
	public PatientAppointmentController(final AppointmentService appointmentService,
			final PatientService patientService) {
		this.appointmentService = appointmentService;
		this.patientService = patientService;
	}

	@InitBinder("appointment")
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		dataBinder.setValidator(new AppointmentValidator());

	}

	@GetMapping(value = "/patient/appointments/table")
	public String generateTable(final Map<String, Object> model) {
		List<LocalDateTime> citas =
				new ArrayList<>(this.appointmentService.findAppointmentByDoctors(
						this.patientService.findCurrentPatient().getGeneralPractitioner().getId()));
		List<LocalDateTime> table = timeTable(LocalDate.now());
		table.removeAll(citas);
		if (table.isEmpty()) {
			model.put("emptylist", true);
		} else {
			model.put("hours", table);

		}
		return "/patient/appointments/timeTable";
	}

	@GetMapping(value = "/patient/appointments/new")
	public String initCreationForm(@RequestParam("fecha") final LocalDateTime fecha,
			final Map<String, Object> model) throws ParseException {
		Appointment appointment = new Appointment();
		appointment.setPatient(this.patientService.findCurrentPatient());

		appointment.setStartTime(fecha);
		appointment.setEndTime(fecha.plusMinutes(7));

		model.put("appointment", appointment);

		return "/patient/appointments/requestAppointment";

	}

	@PostMapping(value = "/patient/appointments/save")
	public String processCreationForm(@Valid final Appointment appointment,
			final BindingResult result) {
		Patient p = this.patientService.findCurrentPatient();
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
		LocalDateTime start =
				LocalDateTime.of(l.getYear(), l.getMonthValue(), l.getDayOfMonth(), 9, 0);
		hours.add(start);
		LocalDateTime end = start.plusHours(1);
		while (start.isBefore(end)) {
			start = start.plusMinutes(7);
			hours.add(start);
		}

		return hours;
	}

	@GetMapping("/patient/appointments/{appointmentId}/delete")
	public String deleteAppointent(@PathVariable("appointmentId") final int appointmentId) {

		Patient patient = this.patientService.findCurrentPatient();
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		Collection<Appointment> appointmentsDone = this.patientService.findAppointmentsDone();

		if (appointment != null
				&& appointment.getPatient().getId() == patient.getId()
				&& !appointmentsDone.contains(appointment)) {
			this.appointmentService.deleteAppointment(appointment);
			return "redirect:/patient/appointments";
		} else {
			return "exception";
		}

	}

	@GetMapping("/patient/appointments")
	public String listAppointmentsPatient(final ModelMap model) {

		Collection<Appointment> appointmentsDelete = this.patientService.findAppointmentsDelete();
		Collection<Appointment> appointmentsDone = this.patientService.findAppointmentsDone();


		if (appointmentsDelete.isEmpty()) {
			model.put("appointmentsDelete", true);
		} else {
			model.put("appointmentsDelete", appointmentsDelete);
		}

		if (appointmentsDone.isEmpty()) {
			model.put("appointmentsDone", true);
		} else {
			model.put("appointmentsDone", appointmentsDone);
		}

		return "/patient/appointments/appointmentsList";
	}
}
