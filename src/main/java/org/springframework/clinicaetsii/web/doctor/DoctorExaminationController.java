package org.springframework.clinicaetsii.web.doctor;



import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Examination;
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
@RequestMapping("/doctor/patients/{patientId}/consultations/{consultationId}/examinations")
public class DoctorExaminationController {



	private ExaminationService examinationService;
	private ConsultationService consultationService;

	@Autowired
	public DoctorExaminationController(final ExaminationService examinationService, final ConsultationService consultationService) {
		this.examinationService = examinationService;
		this.consultationService = consultationService;
	}

	@ModelAttribute("Consultation")
	public Consultation findConsultation(@PathVariable("consultationId") final int consultationId) {
		return this.consultationService.findConsultationById(consultationId);
	}

	@GetMapping(value = "/{examinationId}")
	public String showExaminationDetails(@PathVariable final int examinationId, @PathVariable final int patientId, final Map<String, Object> model) {

		Examination result = this.examinationService.findExaminationsById(examinationId);

		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("patientId", patientId);
			model.put("examination", result);
		}

		return "doctor/examinations/examinationDetails";
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {
		Examination examination = new Examination();
		model.put("examination", examination);
		return "/doctor/examinations/createExaminationForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@PathVariable final int consultationId, @Valid final Examination examination, final BindingResult result) {
		if (result.hasErrors()) {
			return  "/doctor/examinations/createExaminationForm";
		}
		else {
			Consultation current = this.consultationService.findConsultationById(consultationId);
			examination.setStartTime(LocalDateTime.now());
			current.getExaminations().add(examination);
			this.examinationService.saveExamination(examination);
			return "redirect:/doctor/patients/{patientId}/consultations/{consultationId}";
		}
	}
}
