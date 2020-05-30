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
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class AdministrativeMedicineController {

	private final MedicineService	medicineService;
	private PrescriptionService		prescriptionService;


	@Autowired
	public AdministrativeMedicineController(final MedicineService medicineService, final PrescriptionService prescriptionService) {
		this.medicineService = medicineService;
		this.prescriptionService = prescriptionService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/administrative/medicines/{medicineId}")
	public String processDetails(@PathVariable("medicineId") final int medicineId, final Map<String, Object> model) {

		Medicine med = this.medicineService.findMedicineById(medicineId);

		if (med == null) {
			model.put("empty", true);
		} else {
			model.put("medicine", med);
		}

		return "administrative/medicine/medicineDetails";

	}

	@GetMapping(value = "/administrative/medicines")
	public String listMedicines(final Medicine medicine, final BindingResult result, final Map<String, Object> model) {

		Collection<Medicine> results = this.medicineService.findAllMedicines();
		if (results.isEmpty()) {
			model.put("emptylist", true);
		} else {
			model.put("medicines", results);
		}
		return "/administrative/medicine/medicinesList";
	}

	@GetMapping("/administrative/medicines/{medicineId}/edit")
	public String initUpdateForm(@PathVariable("medicineId") final int medicineId, final ModelMap model) {
		Medicine medicine = this.medicineService.findMedicineById(medicineId);
		model.put("medicine", medicine);
		return "/administrative/medicine/updateMedicineForm";
	}

	@PostMapping("/administrative/medicines/{medicineId}/edit")
	public String processUpdateForm(@PathVariable("medicineId") final int medicineId, @Valid final Medicine medicine, final BindingResult result, final ModelMap model) {
		Prescription prescription = this.prescriptionService.findPrescriptionByMedicineId(medicineId);

		if (medicine.getQuantity() < 0) {
			result.rejectValue("quantity", "incorrect_quantity", "La cantidad introducida debe de ser mayor que 0");
		}

		if (result.hasErrors()) {
			model.put("medicine", medicine);
			return "/administrative/medicine/updateMedicineForm";
		} else {
			medicine.setId(medicineId);
			this.medicineService.saveMedicine(medicine);
			return "redirect:/administrative/medicines/{medicineId}";
		}

	}

	@GetMapping(value = "/administrative/medicines/{medicineId}/delete")
	public String initDelete(@PathVariable("medicineId") final int medicineId) {
		Medicine medicine = this.medicineService.findMedicineById(medicineId);
		Prescription prescription = this.prescriptionService.findPrescriptionByMedicineId(medicineId);
		if (!(prescription != null)) {
			this.medicineService.deleteMedicine(medicine);
		}



		return "redirect:/administrative/medicines";
	}

}
