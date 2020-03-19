package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.ConstantService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.web.validator.ConstantValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DoctorConstantController {

	private ConsultationService consultationService;
	private ConstantService constantService;

	@Autowired
	public DoctorConstantController(final ConstantService constantService, final ConsultationService consultationService) {
		this.constantService = constantService;
		this.consultationService = consultationService;
	}

	@InitBinder("constant")
	@RequestMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new")
	public void initBinder(final WebDataBinder dataBinder, @PathVariable("consultationId") final int consultationId) {
		dataBinder.setValidator(new ConstantValidator(this.consultationService, consultationId));
	}

	@ModelAttribute("constantTypes")
	public Collection<ConstantType> populateConstantTypes() {
		return this.constantService.findAllConstantTypes();
	}

	@GetMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new")
	public String initConstantCreation(@PathVariable("consultationId") final Integer consultationId, final ModelMap model) {
		Constant constant = new Constant();
		model.put("consultationId", consultationId);
		model.put("constant", constant);
		return "/doctor/consultations/createOrUpdateConstantForm";
	}

	@PostMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new")
	public String processConstantCreation(@PathVariable("consultationId") final int consultationId, @Valid final Constant constant, final BindingResult result, final ModelMap model) {
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

	@GetMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit")
	public String initConstantEdition(@PathVariable("consultationId") final Integer consultationId, @PathVariable("constantId") final Integer constantId, final ModelMap model) {
		Constant constant = this.constantService.findConstantById(constantId);
		model.put("consultationId", consultationId);
		model.put("constant", constant);
		return "/doctor/consultations/createOrUpdateConstantForm";
	}

	@PostMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit")
	public String processConstantEdition(@PathVariable("consultationId") final Integer consultationId, @Valid final Constant constant, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("consultationId", consultationId);
			model.put("constant", constant);
			return "/doctor/consultations/createOrUpdateConstantForm";
		} else {
			this.constantService.saveConstant(constant);
		}

		return "redirect:/doctor/patients/{patientId}/consultations/{consultationId}";
	}

	@GetMapping("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/delete")
	public String processConstantDeletion(@PathVariable("consultationId") final Integer consultationId, @PathVariable("constantId") final Integer constantId) {
		Consultation consultation = this.consultationService.findConsultationById(consultationId);
		Constant constant = this.constantService.findConstantById(constantId);

		if (constant != null) {
			consultation.getConstants().remove(constant);
			this.consultationService.save(consultation);
			this.constantService.deleteConstant(constant);
		}

		return "redirect:/doctor/patients/{patientId}/consultations/{consultationId}";
	}

}
