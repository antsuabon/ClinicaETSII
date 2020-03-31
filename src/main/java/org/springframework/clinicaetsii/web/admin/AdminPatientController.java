package org.springframework.clinicaetsii.web.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPatientController {


	private final PatientService patientService;

	@Autowired
	public AdminPatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping("/admin/patients")
	public String listPatients(final ModelMap model) {

		Collection<Patient> patients = this.patientService.findPatients();

		if(patients.isEmpty()) {
			model.addAttribute("emptyList", true);
		} else {
			model.addAttribute("patients",patients);
		}

		return "/admin/patients/patientsList";
	}

	@GetMapping("/admin/patients/{patientId}")
	public ModelAndView showPatient(@PathVariable("patientId") final int patientId) {
		ModelAndView mav = new ModelAndView("/admin/patients/patientDetails");
		mav.addObject(this.patientService.findPatientById(patientId));
		return mav;
	}


}
