package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.ConstantService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorConstantController {

	private ConsultationService consultationService;
	private ConstantService constantService;

	@Autowired
	public DoctorConstantController(final ConstantService constantService, final ConsultationService consultationService) {
		this.constantService = constantService;
		this.consultationService = consultationService;
	}

	@ModelAttribute("constantTypes")
	public Collection<ConstantType> populateConstantTypes() {
		return this.constantService.findAllConstantTypes();
	}

	@GetMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new")
	public String initConstantCreation(@PathParam("consultationId") final Integer consultationId, final ModelMap model) {
		Constant constant = new Constant();
		model.put("consultationId", consultationId);
		model.put("constant", constant);
		return "/doctor/consultations/createOrUpdateConstantForm";
	}

	@PostMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new")
	public String processConstantCreation(@PathParam("consultationId") final int consultationId, @Valid final Constant constant, final BindingResult result, final ModelMap model) {
		Consultation consultation = this.consultationService.findConsultationById(consultationId);
		if (result.hasErrors()) {
			model.put("consultationId", consultationId);
			model.put("constant", constant);
			return "/doctor/consultations/createOrUpdateConstantForm";
		} else {
			this.constantService.saveConstant(constant);
			consultation.getConstants().add(constant);
			this.consultationService.save(consultation);
		}

		return "redirect:/doctor/patients/{patientId}/consultations/{consultationId}";
	}

}
