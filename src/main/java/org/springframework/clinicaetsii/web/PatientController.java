package org.springframework.clinicaetsii.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")
public class PatientController {

	private PatientService patientService;

	@Autowired
	public PatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping(value = "/{patientId}/edit")
	public String initUpdateOwnerForm(@PathVariable("patientId") final int patientId, final Model model) {
		Patient patient = this.patientService.findById(patientId);
		model.addAttribute(patient);
		return  "patients/updatePatientForm";
	}

	@PostMapping(value = "/{patientId}/edit")
	public String processUpdateOwnerForm(@Valid final Patient patient, final BindingResult result,
			@PathVariable("patientId") final int patientId) {
		if (result.hasErrors()) {
			return "patients/updatePatientForm";
		}
		else {
			patient.setId(patientId);
			this.patientService.save(patient);
			return "redirect:/{patientId}";
		}
	}

	@GetMapping("/{patientId}")
	public String show(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {

		Patient results = this.patientService.findById(patientId);
		model.put("patient", results);

		this.patientService.save(results);
		return "patients/patientDetails";
	}

}
