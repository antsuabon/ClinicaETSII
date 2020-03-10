/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.doctor.PatientService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class PatientController {

	private final PatientService patientService;


	@Autowired
	public PatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/doctors/listPatients")
	public String listPatients(final Patient patient, final BindingResult result, final Map<String, Object> model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();
		System.out.println(username);
		int doctorId = this.patientService.findUserIdByUsername(username);

		Collection<Patient> results = this.patientService.listPatients(doctorId);
		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patients", results);
		}
		return "/doctors/patientsList";
	}

	@GetMapping(value = "/doctors/patientsList/{patientId}")
	public String listConsultationsPatient(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {
		Collection<Consultation> results = this.patientService.findConsultsByPatientId(patientId);
		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("consultations", results);
		}
		return "/doctors/consultationsList";
	}

	@GetMapping(value = "/doctors/consultationsList/{consultationId}")
	public String showConsultationDetails(@PathVariable("consultationId") final int consultationId, final Map<String, Object> model) {

		Consultation result = this.patientService.showConsultation(consultationId);
		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("consultation", result);
		}
		return "/doctors/consultationDetails";
	}

}
