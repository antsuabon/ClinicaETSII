package org.springframework.clinicaetsii.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	/*public Patient findPatient(@RequestParam(name = "patientId") int patientId) {
		return this.prescriptionService.findPatientById(patientId);
	}*/

	@GetMapping(value = "/prescriptions")
	public String processFind(@RequestParam(name = "patientId", required = true) int patientId, final Prescription prescription, final BindingResult result, final Map<String, Object> model) {

		Collection<Prescription> prescriptions = this.prescriptionService.findPrescriptionsFromPatient(patientId);
		if (prescriptions.isEmpty()) {
			model.put("emptylist", true);
		} else {
			List<Prescription> prescriptionList = new ArrayList<>(prescriptions);
			model.put("prescriptions", prescriptionList);

		}
		return "/prescriptions/prescriptionList";
	}

}
