package org.springframework.clinicaetsii.web.patient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PatientPrescriptionController {

	private PrescriptionService prescriptionService;

	private PatientService patientService;

	@Autowired
	public PatientPrescriptionController(final PrescriptionService prescriptionService,
			final PatientService patientService) {
		this.prescriptionService = prescriptionService;
		this.patientService = patientService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/patient/prescriptions")
	public String processFind(final Map<String, Object> model) {

		Patient patient = this.patientService.findCurrentPatient();
		Collection<Prescription> prescriptions =
				this.prescriptionService.findPrescriptionsByPatientUsername(patient.getUsername());
		if (prescriptions.isEmpty()) {
			model.put("emptylist", true);
		} else {
			List<Prescription> prescriptionList = new ArrayList<>(prescriptions);
			model.put("prescriptions", prescriptionList);

		}
		return "/prescriptions/prescriptionList";
	}

	@GetMapping(value = "/patient/prescriptions/{prescriptionId}")
	public String processDetails(@PathVariable(name = "prescriptionId") final int prescriptionId,
			final Map<String, Object> model) {

		Prescription pres = this.prescriptionService.findPrescriptionById(prescriptionId);

		if (pres == null) {
			model.put("empty", true);
		} else {
			model.put("consultation", pres);
		}

		return "/prescriptions/prescriptionDetails";
	}

}
