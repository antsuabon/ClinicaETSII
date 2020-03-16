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

package org.springframework.clinicaetsii.web.administrative;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdministrativePatientController {

	private PatientService patientService;


	@Autowired
	public AdministrativePatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
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

		return "/administrative/patientsList";
	}

	@GetMapping(value = "/administrative/patients/{patientId}/doctors")
	public String processFind(@PathVariable("patientId") final int patientId, final Doctor doctor, final BindingResult result, final Map<String, Object> model) {

		Doctor d = this.patientService.findDoctorByPatient(patientId);

		if (d.equals(null)) {
			model.put("emptylist", true);
		} else {
			model.put("patientId", patientId);
			model.put("doctor", d);

		}
		return "/administrative/doctorsList";
	}

}
