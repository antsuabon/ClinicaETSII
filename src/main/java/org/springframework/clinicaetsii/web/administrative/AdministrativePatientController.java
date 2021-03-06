/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.springframework.clinicaetsii.web.administrative;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.web.validator.PatientValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrativePatientController {

	private PatientService patientService;
	private DoctorService doctorService;
	private AuthoritiesService authoritiesService;
	private UserService userService;

	@Autowired
	public AdministrativePatientController(final PatientService patientService,
			final DoctorService doctorService, final AuthoritiesService authoritiesService,
			final UserService userService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.authoritiesService = authoritiesService;
		this.userService = userService;
	}

	@InitBinder("patient")
	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PatientValidator(this.patientService, this.userService));
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/administrative/patients")
	public String listPatients(final Map<String, Object> model) {
		Collection<Patient> results = this.patientService.findPatients();

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patients", results);
		}

		return "/administrative/patients/patientsList";
	}



	@ModelAttribute("doctors")
	public Collection<Doctor> populateDoctors() {
		return this.doctorService.findAllDoctors();
	}

	@GetMapping("/administrative/patients/new")
	public String initCreation(final Map<String, Object> model) {

		Patient patient = new Patient();
		model.put("patient", patient);
		model.put("administrative", this.patientService.findCurrentAdministrative());

		return "/administrative/patients/createPatientForm";

	}


	@PostMapping("/administrative/patients/new")
	public String processCreation(@Valid final Patient patient, final BindingResult result) {

		if (result.hasErrors()) {
			return "/administrative/patients/createPatientForm";

		} else {

			patient.setEnabled(true);
			patient.setPassword("123456789");
			this.patientService.save(patient);
			this.authoritiesService.saveAuthorities(String.valueOf(patient.getId()), "patient");
			return "redirect:/administrative/patients/" + patient.getId();
		}
	}

	@GetMapping("/administrative/patients/{patientId}")
	public ModelAndView showPatient(@PathVariable("patientId") final int patientId) {
		ModelAndView mav = new ModelAndView("/administrative/patients/patientDetails");
		mav.addObject(this.patientService.findPatientById(patientId));
		return mav;
	}

}
