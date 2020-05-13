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

package org.springframework.clinicaetsii.web.admin;

import java.util.Collection; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Administrative;
import org.springframework.clinicaetsii.service.AdministrativeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminAdministrativeController {

	private AdministrativeService administrativeService;


	@Autowired
	public AdminAdministrativeController(final AdministrativeService administrativeService) {
		this.administrativeService = administrativeService;
	}

	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/admin/administratives")
	public String listAdministratives(final Map<String, Object> model) {
		Collection<Administrative> results = this.administrativeService.findAllAdministratives();

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("administratives", results);
		}

		return "/admin/administratives/administrativesList";
	}

	@GetMapping("/admin/administratives/{administrativeId}")
	public ModelAndView showAdministrative(@PathVariable("administrativeId") final int administrativeId) {
		ModelAndView mav = new ModelAndView("/admin/administratives/administrativeDetails");

		Administrative administrative = this.administrativeService.findAdministrativeById(administrativeId);

		mav.addObject(administrative);

		
		return mav;
	}

	@GetMapping("/admin/administratives/{administrativeId}/delete")
	public String initDelete(@PathVariable("administrativeId") final int administrativeId,
			final Map<String, Object> model) {

		Administrative administrative = this.administrativeService.findAdministrativeById(administrativeId);

			this.administrativeService.delete(administrative);

			return "redirect:/admin/administratives";


	}

}
