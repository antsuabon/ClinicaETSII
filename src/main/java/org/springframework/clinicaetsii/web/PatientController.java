package org.springframework.clinicaetsii.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

	private PatientService patientService;

	@Autowired
	public PatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping(value = "/edit")
	public String initUpdatePatientForm(final Model model) {
		Patient patient = this.patientService.findPatient();
		model.addAttribute("patient", patient);
		return  "patients/updatePatientForm";
	}

	@PostMapping(value = "/edit")
	public String processUpdatePatientForm(@Valid final Patient patient, final BindingResult result, final ModelMap model) {

		if (result.hasErrors()) {

			model.addAttribute("patient", patient);
			return "patients/updatePatientForm";

		} else {

			this.patientService.save(patient);


		}

		return "redirect:/patient";
	}

	@GetMapping
	public String show(final Map<String, Object> model) {

		Patient results = this.patientService.findPatient();
		model.put("patient", results);

		return "patients/patientDetails";
	}

}
