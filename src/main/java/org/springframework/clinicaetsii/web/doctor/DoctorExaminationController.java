package org.springframework.clinicaetsii.web.doctor;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.service.ExaminationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/doctor/patients/{patientId}/consultations{consultationId}/examinations")
public class DoctorExaminationController {

	
	
	private ExaminationService examinationService;
	private ConsultationService consultationService;
	
	@Autowired
	public DoctorExaminationController(final ExaminationService examinationService, ConsultationService consultationService) {
		this.examinationService = examinationService;
		this.consultationService = consultationService;
	}
	
	@ModelAttribute("Consultation")
	public Consultation findConsultation(@PathVariable("consultationId") int consultationId) {
		return this.consultationService.findConsultationById(consultationId);
	}

	@GetMapping(value = "/examination/new")
	public String initCreationForm(final ModelMap model) {

		Examination examination = new Examination();
		model.put("examination", examination);
		return "doctor/examination/createExaminationForm";
	}

	@PostMapping(value = "/examination/new")
	public String processCreationForm( @Valid Examination examination,Consultation consultation, BindingResult result) {
		if (result.hasErrors()) {
			return  "doctor/examination/createExaminationForm";
		}
		else {
			consultation.getExaminations().add(examination);
			this.examinationService.saveExamination(examination);
			return "redirect:/doctor/patients/{patientId}/consultations{consultationId}/examinations/" + examination.getId();
		}
	}
}
