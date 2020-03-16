package org.springframework.clinicaetsii.web.administrative;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin")
public class AdministrativePatientController {

	private PatientService patientService;

	@Autowired
	public AdministrativePatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping(value = "/patient/new")
	public String initCreationForm(final ModelMap model) {
		Patient patient = new Patient();;
		model.put("patient", patient);
		return "/admin/patients/createPatient";
	}

	@PostMapping(value = "/patient/new")
	public String processCreationForm(@Valid final Patient patient, final BindingResult result, final ModelMap model) {

		if (result.hasErrors()) {

			model.put("patient", patient);
			return "/admin/patients/createPatient";

		} else {

           this.patientService.savePatient(patient);

           return "redirect:/patient/{patientId}";
		}
	}

}
