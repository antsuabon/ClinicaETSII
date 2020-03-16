package org.springframework.clinicaetsii.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MedicineController {
	
	private final MedicineService medicineService;
	
	@Autowired
	public MedicineController(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/patient/medicines/{medicineId}")
	public String processDetails(@PathVariable(name = "medicineId") int medicineId, final Map<String, Object> model) {
		
		Medicine med = this.medicineService.findMedicineById(medicineId);
		
		if (med == null) {
			model.put("empty", true);
		} else {
			model.put("medicine", med);
		}

		return "/medicines/medicineDetails";

	@GetMapping(value = "/anonymous/medicines")
	public String listMedicines(final Medicine medicine, final BindingResult result, final Map<String, Object> model) {

		Collection<Medicine> results = this.medicineService.findAllMedicines();
		if (results.isEmpty()) {
			model.put("emptylist", true);
		} else {
			model.put("medicines", results);
		}
		return "/anonymous/medicines/medicinesList";
	}

}
