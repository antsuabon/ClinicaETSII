package org.springframework.clinicaetsii.web.doctor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.web.validator.ConsultationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorConsultationController {

	private ConsultationService consultationService;
	private AppointmentService appointmentService;

	@Autowired
	public DoctorConsultationController(final ConsultationService consultationService, final AppointmentService appointmentService) {
		this.consultationService = consultationService;
		this.appointmentService = appointmentService;
	}

	@GetMapping("/doctor/patients/{patientId}/consultations")
	public String listConsultationsPatient(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {
		Collection<Consultation> results = this.consultationService.findConsultationsByPatientId(patientId);

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patientId", patientId);
			model.put("consultations", results);
		}

		return "/doctor/consultations/consultationsList";
	}


	@GetMapping("/doctor/patients/{patientId}/consultations/{consultationId}")
	public String showConsultationDetails(@PathVariable("patientId") final int patientId, @PathVariable("consultationId") final int consultationId, final Map<String, Object> model) {
		Consultation result = this.consultationService.findConsultationById(consultationId);

		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("patientId", patientId);
			model.put("consultation", result);
		}

		return "/doctor/consultations/consultationDetails";
	}

	@ModelAttribute("dischargeTypes")
	public Collection<DischargeType> populateDischargeTypes() {
		return this.consultationService.findDischargeTypes();
	}

	@InitBinder("consultation")
	public void initBinder(final WebDataBinder dataBinder, @ModelAttribute("appointmentId") final Integer appointmentId, @PathVariable(name = "consultationId", required = false) final Integer consultationId) {
		dataBinder.setValidator(new ConsultationValidator(this.appointmentService, this.consultationService, appointmentId, consultationId));
	}

	@GetMapping("/doctor/patients/{patientId}/consultations/new")
	public String initCreationForm(@RequestParam("appointmentId") final int appointmentId, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		Consultation consultation = new Consultation();
      	consultation.setAppointment(appointment);
      	consultation.setStartTime(LocalDateTime.now());
		model.put("consultation", consultation);
		return "/doctor/consultations/createOrUpdateConsultationForm";
	}

	@PostMapping("/doctor/patients/{patientId}/consultations/new")
	public String processCreationForm(@ModelAttribute("appointmentId") final int appointmentId, @Valid final Consultation consultation, final BindingResult result, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);;
      	consultation.setAppointment(appointment);

		if (result.hasErrors()) {
			model.put("consultation", consultation);
			return "/doctor/consultations/createOrUpdateConsultationForm";
		}
		else {
			this.consultationService.save(consultation);
			return "redirect:/doctor/patients/{patientId}/consultations/" + consultation.getId();
		}
	}

	@ModelAttribute("allDiagnoses")
	public Collection<Diagnosis> populateAllDiagnoses() {
		return this.consultationService.findAllDiagnoses();
	}

	@GetMapping("/doctor/patients/{patientId}/consultations/{consultationId}/edit")
	public String initUpdateForm(@PathVariable("consultationId") final int consultationId, final ModelMap model) {
		Consultation consultation = this.consultationService.findConsultationById(consultationId);
		model.put("consultation", consultation);
		return "/doctor/consultations/createOrUpdateConsultationForm";
	}

	@PostMapping("/doctor/patients/{patientId}/consultations/{consultationId}/edit")
	public String processUpdateForm(@PathVariable("consultationId") final int consultationId, @Valid final Consultation consultation, final BindingResult result, final ModelMap model) {
		Consultation oldConsultation = this.consultationService.findConsultationById(consultationId);

		consultation.setExaminations(oldConsultation.getExaminations());
		consultation.setAppointment(oldConsultation.getAppointment());
      	consultation.setConstants(oldConsultation.getConstants());

		if (result.hasErrors()) {
			model.put("consultation", consultation);
			return "/doctor/consultations/createOrUpdateConsultationForm";
		}
		else {


			if (consultation.getDischargeType() != null) {
				consultation.setEndTime(LocalDateTime.now());
			}

			this.consultationService.save(consultation);
			return "redirect:/doctor/patients/{patientId}/consultations/" + consultation.getId();
		}
	}


}
