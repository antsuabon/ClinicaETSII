package org.springframework.clinicaetsii.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PrescriptionController {
	
	private final PrescriptionService prescriptionService;

	@Autowired
	public PrescriptionController(final PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}
	
	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/patient/prescriptions")
	public String processFind(final Map<String, Object> model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<Prescription> prescriptions = this.prescriptionService.findPrescriptionsFromPatient(auth.getName());;
		if (prescriptions.isEmpty()) {
			model.put("emptylist", true);
		} else {
			List<Prescription> prescriptionList = new ArrayList<>(prescriptions);
			model.put("prescriptions", prescriptionList);

		}
		return "/prescriptions/prescriptionList";
	}
	
	@GetMapping(value = "/patient/prescriptions/{prescriptionId}")
	public String processDetails(@PathVariable(name = "prescriptionId") int prescriptionId, final Map<String, Object> model) {
		
		Prescription pres = this.prescriptionService.findPrescriptionById(prescriptionId);
		
		if (pres == null) {
			model.put("empty", true);
		} else {
			model.put("consultation", pres);
		}

		return "/prescriptions/prescriptionDetails";
	}

}
