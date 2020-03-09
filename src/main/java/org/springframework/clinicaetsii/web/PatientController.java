package org.springframework.clinicaetsii.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")
public class PatientController {

	private PatientService patientService;

	@Autowired
	public PatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping("/{patientId}")
	public String show(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {

		Patient results = this.patientService.findById(patientId);
		model.put("patient", results);

		this.patientService.save(results);
		return "patients/patientDetails";
	}

}
