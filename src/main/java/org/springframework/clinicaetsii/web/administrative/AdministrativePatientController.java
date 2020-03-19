package org.springframework.clinicaetsii.web.administrative;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/administrative")
public class AdministrativePatientController {

	private PatientService patientService;

	private AuthoritiesService authoritiesService;

	@Autowired
	public AdministrativePatientController(final PatientService patientService, final AuthoritiesService authoritiesService) {
		this.patientService = patientService;
		this.authoritiesService = authoritiesService;
	}

	@InitBinder("patient")
	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PatientValidator(this.patientService));
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/patients/new")
	public String initCreation(final Map<String, Object> model) {

		Patient patient = new Patient();
		model.put("patient", patient);

		return "/administrative/patients/createPatientForm";

	}


	@PostMapping("/patients/new")
	public String processCreation(@Valid final Patient patient, final BindingResult result) {

		if(result.hasErrors()) {

			return "/administrative/patients/createPatientForm";

		} else {

			patient.setEnabled(true);
			patient.setPassword("123456789");
			this.patientService.save(patient);
			this.authoritiesService.saveAuthorities(String.valueOf(patient.getId()), "patient");
			return "redirect:/administrative/patients/" + patient.getId();
		}
	}

	@GetMapping("/patients/{patientId}")
	public ModelAndView showOwner(@PathVariable("patientId") final int patientId) {
		ModelAndView mav = new ModelAndView("administrative/patients/patientDetails");
		mav.addObject(this.patientService.findPatientById(patientId));
		return mav;
	}



}
