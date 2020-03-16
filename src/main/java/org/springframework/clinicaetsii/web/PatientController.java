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

package org.springframework.clinicaetsii.web;

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

	@GetMapping(value = "/patients/doctors")
	public String processFind(final Doctor doctor, final BindingResult result, final Map<String, Object> model) {

		Patient p = this.patientService.findPatientByUsername();
		System.out.println("El paciente es: " + p.getFullName() + " con id: " + p.getId() + " con username " + p.getUsername() + " y direccion " + p.getAddress());
		Doctor d = this.patientService.findDoctorByPatient(p.getId());
		System.out.println("Su médico es: " + p.getGeneralPractitioner().getId());

		if (d.equals(null)) {
			model.put("emptylist", true);
		} else {
			model.put("doctor", d);

		}
		return "/patients/doctors/doctorsList";
	}

}
