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
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class DoctorPatientController {

	private PatientService patientService;

	@Autowired
	public DoctorPatientController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping("/doctor/patients")
	public String listPatients(final Map<String, Object> model) {
		Collection<Patient> results = this.patientService.findCurrentDoctorPatients();

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patients", results);
		}

		return "/doctor/patientsList";
	}

}
