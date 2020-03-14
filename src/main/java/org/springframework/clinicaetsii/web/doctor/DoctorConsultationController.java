package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/doctor/patients/{patientId}/consultations")
public class DoctorConsultationController {

	private ConsultationService consultationService;
	private AppointmentService appointmentService;

	@Autowired
	public DoctorConsultationController(final ConsultationService consultationService, final AppointmentService appointmentService) {
		this.consultationService = consultationService;
		this.appointmentService = appointmentService;
	}

	@GetMapping
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

	@GetMapping("/{consultationId}")
	public String showConsultationDetails(@PathVariable("consultationId") final int consultationId, final Map<String, Object> model) {
		Consultation result = this.consultationService.findConsultationById(consultationId);

		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("consultation", result);
		}

		return "/doctor/consultations/consultationDetails";
	}

	@ModelAttribute
	public void populateDischargeTypes(final Model model) {
		Collection<DischargeType> dischargeTypes = this.consultationService.findDischargeTypes();

		Map<String, DischargeType> map = new HashMap<String, DischargeType>();
		for (DischargeType dischargeType : dischargeTypes) {
			map.put(String.valueOf(dischargeType.getId()), dischargeType);
		}

		model.addAttribute("dischargeTypes", map);
	}

	@GetMapping("/new")
	public String initCreationForm(@RequestParam("appointmentId") final int appointmentId, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);
		Consultation consultation = new Consultation();
      	consultation.setAppointment(appointment);
		model.put("consultation", consultation);
		return "/doctor/consultations/createOrUpdateConsultation";
	}

	@PostMapping("/new")
	public String processCreationForm(@ModelAttribute("appointmentId") final int appointmentId, @Valid final Consultation consultation, final BindingResult result, final ModelMap model) {
		Appointment appointment = this.appointmentService.findAppointmentById(appointmentId);;
      	consultation.setAppointment(appointment);

		if (result.hasErrors()) {
			model.put("consultation", consultation);
			return "/doctor/consultations/createOrUpdateConsultation";
		}
		else {
			this.consultationService.save(consultation);
			return "redirect:/doctor/patients/{patientId}/consultations/" + consultation.getId();
		}
	}

	@GetMapping("/{consultationId}/edit")
	public String initUpdateForm(@PathVariable("consultationId") final int consultationId, final ModelMap model) {
		Consultation consultation = this.consultationService.findConsultationById(consultationId);
		model.put("consultation", consultation);
		return "/doctor/consultations/createOrUpdateConsultation";
	}

	@PostMapping("/{consultationId}/edit")
	public String processUpdateForm(@PathVariable("consultationId") final int consultationId, @Valid final Consultation consultation, final BindingResult result, final ModelMap model) {
		Consultation oldConsultation = this.consultationService.findConsultationById(consultationId);

		consultation.setAppointment(oldConsultation.getAppointment());
      	consultation.setConstants(oldConsultation.getConstants());
      	consultation.setDiagnoses(oldConsultation.getDiagnoses());

		if (result.hasErrors()) {
			model.put("consultation", consultation);
			return "/doctor/consultations/createOrUpdateConsultation";
		}
		else {
			this.consultationService.save(consultation);
			return "redirect:/doctor/patients/{patientId}/consultations/" + consultation.getId();
		}
	}


}
