package org.springframework.clinicaetsii.web.admin;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.exceptions.DeletePatientException;
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

		if (patients.isEmpty()) {
			model.addAttribute("emptyList", true);
		} else {
			model.addAttribute("patients", patients);
		}

		return "/admin/patients/patientsList";
	}

	@GetMapping("/admin/patients/{patientId}")
	public ModelAndView showPatient(@PathVariable("patientId") final int patientId) {
		ModelAndView mav = new ModelAndView("/admin/patients/patientDetails");

		Patient patient = this.patientService.findPatientById(patientId);

		mav.addObject(patient);
		mav.addObject("isErasable", this.patientService.isErasable(patient));

		return mav;
	}

	@GetMapping("/admin/patients/{patientId}/delete")
	public String deletePatient(@PathVariable("patientId") final int patientId,
			final ModelMap model) {
		Patient patient = this.patientService.findPatientById(patientId);

		if (patient != null) {
			try {
				this.patientService.delete(patient);
			} catch (DeletePatientException e) {
				return "redirect:/admin/patients/{patientId}";
			}
		}

		return "redirect:/admin/patients";
	}



}
