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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrativeDoctorController {

	private DoctorService		doctorService;
	private AuthoritiesService	authoritiesService;
	private UserService			userService;


	@Autowired
	public AdministrativeDoctorController(final DoctorService doctorService, final AuthoritiesService authoritiesService, final UserService userService) {
		this.doctorService = doctorService;
		this.authoritiesService = authoritiesService;
		this.userService = userService;
	}

	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/administrative/doctors")
	public String listDoctors(final Map<String, Object> model) {
		Collection<Doctor> results = this.doctorService.findAllDoctors();

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("doctors", results);
		}

		return "/administrative/doctors/doctorsList";
	}

	@GetMapping("/administrative/doctors/{doctorId}")
	public ModelAndView showDoctor(@PathVariable("doctorId") final int doctorId) {
		ModelAndView mav = new ModelAndView("/administrative/doctors/doctorDetails");
		mav.addObject(this.doctorService.findDoctorById(doctorId));
		return mav;
	}

}