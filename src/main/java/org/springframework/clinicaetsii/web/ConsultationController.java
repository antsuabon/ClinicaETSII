package org.springframework.clinicaetsii.web;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConsultationController {

	private ConsultationService consultationService;

	private static final String VIEW_CONSULTATION_CREATE_FORM = "consultations/createConsultationForm";

	@Autowired
	public ConsultationController(final ConsultationService consultationService) {
		this.consultationService = consultationService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/doctors/{doctorId}/consultations/new")
	public String initCreationForm(@PathVariable("doctorId") final int doctorId, @RequestParam("appointmentId") final int appointmentId, final Map<String, Object> model) {
		Consultation consultation = new Consultation();
		consultation.setStartTime(LocalDateTime.now());
		model.put("consultation", consultation);
		return ConsultationController.VIEW_CONSULTATION_CREATE_FORM;
	}

	@PostMapping("/doctors/{doctorId}/consultations/new")
	public String processCreationForm(@Valid final Consultation consultation, final BindingResult result) {
		if (result.hasErrors()) {
			return ConsultationController.VIEW_CONSULTATION_CREATE_FORM;
		}
		else {
			consultation.setEndTime(LocalDateTime.now());
			//consultationService.saveConsultation(consultation);

			return "redirect:/consultations/" + consultation.getId();
		}
	}

}
