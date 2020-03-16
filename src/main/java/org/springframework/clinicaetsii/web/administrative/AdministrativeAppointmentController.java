/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.clinicaetsii.web.administrative;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class AdministrativeAppointmentController {

	private final AppointmentService		appointmentService;
	private final DoctorService				doctorService;
	private final PatientService			patientService;

	private static final DateTimeFormatter	FORMATTER				= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static final DateTimeFormatter	TO_LOCAL_DATE_TIME_ISO	= DateTimeFormatter.ISO_DATE_TIME;


	@Autowired
	public AdministrativeAppointmentController(final AppointmentService appointmentService, final DoctorService doctorService, final PatientService patientService) {
		this.appointmentService = appointmentService;
		this.doctorService = doctorService;
		this.patientService = patientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");

	}

	@GetMapping(value = "/administrative/appointment/doctor/{doctorId}/patient/{patientId}/table")
	public String generateTableAdmin(@PathVariable("doctorId") final int doctorId, @PathVariable("patientId") final int patientId, final Map<String, Object> model) {
		List<LocalDateTime> citas = new ArrayList<>(this.appointmentService.findAppointmentByDoctors(doctorId));
		List<LocalDateTime> table = this.timeTable(LocalDate.now());

		table.removeAll(citas);
		if (table.isEmpty()) {
			model.put("emptylist", true);
		} else {

			model.put("doctor", doctorId);
			model.put("formato", AdministrativeAppointmentController.FORMATTER);
			model.put("hours", table);
			model.put("patientId", patientId);

		}
		return "/administrative/timeTable";
	}

	@GetMapping(value = "/administrative/appointment/new")
	public String initCreationForm(final Map<String, Object> model, @RequestParam(name = "fecha") final String fecha, @RequestParam(name = "doctorId") final String doctorId, @RequestParam(name = "patientId") final int patientId, final String role) {
		Appointment appointment = new Appointment();

		LocalDateTime formatedDate = LocalDateTime.parse(fecha, AdministrativeAppointmentController.TO_LOCAL_DATE_TIME_ISO);

		int id = Integer.parseInt(doctorId);
		Doctor doctor = this.doctorService.findDoctorById(id);

		appointment.setStartTime(formatedDate);
		appointment.setEndTime(formatedDate.plusMinutes(7));

		model.put("doctor", doctor);
		model.put("patientId", patientId);
		model.put("formato", AdministrativeAppointmentController.FORMATTER);
		model.put("appointment", appointment);

		return "/administrative/requestAppointment";

	}

	@PostMapping(value = "/administrative/appointment/save")
	public String processCreationForm(@ModelAttribute("startTime") final String startTime, @ModelAttribute("endTime") final String endTime, @ModelAttribute("patientId") final int patientId, @ModelAttribute("prioridad") final String prioridad,
		final BindingResult result) {
		Patient p = this.patientService.findPatientById(patientId);
		Appointment res = new Appointment();
		res.setStartTime(LocalDateTime.parse(startTime, AdministrativeAppointmentController.TO_LOCAL_DATE_TIME_ISO));
		res.setEndTime(LocalDateTime.parse(endTime, AdministrativeAppointmentController.TO_LOCAL_DATE_TIME_ISO));
		res.setPatient(p);
		res.setPriority(Boolean.parseBoolean(prioridad));
		System.out.println(res);
		if (result.hasErrors()) {

			return "redirect:/administrative/patients/" + patientId + "/doctors";

		} else {

			this.appointmentService.saveAppointment(res);
			return "redirect:/administrative/patients/" + patientId + "/doctors";
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
