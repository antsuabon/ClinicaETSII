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

	private final AppointmentService		appointmentService;
	private final DoctorService				doctorService;
	private final PatientService			patientService;

	private static final DateTimeFormatter	FORMATTER				= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static final DateTimeFormatter	TO_LOCAL_DATE_TIME_ISO	= DateTimeFormatter.ISO_DATE_TIME;


	@Autowired
	public AppointmentController(final AppointmentService appointmentService, final DoctorService doctorService, final PatientService patientService) {
		this.appointmentService = appointmentService;
		this.doctorService = doctorService;
		this.patientService = patientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");

	}

	@GetMapping(value = "/appointment/doctor/{doctorId}/table")
	public String generateTable(@PathVariable("doctorId") final int doctorId, final Map<String, Object> model) {
		List<LocalDateTime> citas = new ArrayList<>(this.appointmentService.findAppointmentByDoctors(doctorId));
		List<LocalDateTime> table = this.timeTable(LocalDate.now());

		table.removeAll(citas);
		if (table.isEmpty()) {
			model.put("emptylist", true);
		} else {

			model.put("doctor", doctorId);
			model.put("formato", AppointmentController.FORMATTER);
			model.put("hours", table);

		}
		return "/patients/doctors/timeTable";
	}

	@GetMapping(value = "/appointment/new")
	public String initCreationForm(final Map<String, Object> model, @RequestParam(name = "fecha") final String fecha, @RequestParam(name = "doctorId") final String doctorId, final String role) {
		Appointment appointment = new Appointment();

		LocalDateTime formatedDate = LocalDateTime.parse(fecha, AppointmentController.TO_LOCAL_DATE_TIME_ISO);

		int id = Integer.parseInt(doctorId);
		Doctor doctor = this.doctorService.findDoctorById(id);

		appointment.setStartTime(formatedDate);
		appointment.setEndTime(formatedDate.plusMinutes(7));

		model.put("doctor", doctor);
		model.put("formato", AppointmentController.FORMATTER);
		model.put("appointment", appointment);

		return "/patients/doctors/requestAppointment";

	}

	@PostMapping(value = "/appointment/save")
	public String processCreationForm(@ModelAttribute("startTime") final String startTime, @ModelAttribute("endTime") final String endTime, final BindingResult result) {
		Patient p = this.patientService.findPatientByUsername();
		Appointment res = new Appointment();
		res.setStartTime(LocalDateTime.parse(startTime, AppointmentController.TO_LOCAL_DATE_TIME_ISO));
		res.setEndTime(LocalDateTime.parse(endTime, AppointmentController.TO_LOCAL_DATE_TIME_ISO));
		res.setPatient(p);

		if (result.hasErrors()) {
			return "redirect:/patients/doctors/";

		} else {

			this.appointmentService.saveAppointment(res);
			return "redirect:/patients/doctors/";
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


	@GetMapping("/doctors/{doctorId}/appointments")
	public String processListByDoctor(@PathVariable("doctorId") final int doctorId, final Map<String, Object> model) {

		Collection<Appointment> results = this.appointmentService.findAllAppointmentsByDoctorId(doctorId);
		model.put("appointments", results);

		return "appointments/appointmentsListByDoctor";
	}

}
