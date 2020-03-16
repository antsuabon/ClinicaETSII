
package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class DoctorPrescriptionController {

	private PrescriptionService	prescriptionService;
	private PatientService		patientService;


	@Autowired
	public DoctorPrescriptionController(final PrescriptionService prescriptionService, final PatientService patientService) {
		this.prescriptionService = prescriptionService;
		this.patientService = patientService;
	}

	@GetMapping("/doctor/patients/{patientId}/prescriptions")
	public String listPrescriptionPatient(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {
		Collection<Prescription> results = this.prescriptionService.findPrescriptionsByPatientId(patientId);

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patientId", patientId);
			model.put("prescriptions", results);
		}

		return "/doctor/prescriptions/prescriptionsList";
	}

	@GetMapping("/doctor/patients/{patientId}/prescriptions/{prescriptionId}")
	public String showConsultationDetails(@PathVariable("prescriptionId") final int prescriptionId, final Map<String, Object> model) {
		Prescription result = this.prescriptionService.findPrescriptionById(prescriptionId);

		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("prescription", result);
		}

		return "/doctor/prescriptions/prescriptionDetails";
	}

	@GetMapping(value = "/doctor/patients/{patientId}/prescriptions/{prescriptionId}/delete")
	public String initDelete(@PathVariable("prescriptionId") final int prescriptionId, final Map<String, Object> model) {

		Prescription prescription = this.prescriptionService.findPrescriptionById(prescriptionId);

		this.prescriptionService.deletePrescription(prescription);

		DoctorPatientController c = new DoctorPatientController(this.patientService);

		return c.listPatients(model);
	}

}
