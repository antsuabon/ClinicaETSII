package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor/patients/{patientId}/consultations")
public class DoctorConsultationController {

	private ConsultationService consultationService;

	@Autowired
	public DoctorConsultationController(final ConsultationService consultationService) {
		this.consultationService = consultationService;
	}

	@GetMapping
	public String listConsultationsPatient(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {
		Collection<Consultation> results = this.consultationService.findConsultationsByPatientId(patientId);

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patientId", patientId);
			model.put("consultations", results);
		}

		return "/doctor/consultations/consultationsList";
	}

	@GetMapping("/{consultationId}")
	public String showConsultationDetails(@PathVariable("patientId") final int patientId, @PathVariable("consultationId") final int consultationId, final Map<String, Object> model) {
		Consultation result = this.consultationService.findConsultationById(consultationId);

		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("patientId", patientId);
			model.put("consultation", result);
		}

		return "/doctor/consultations/consultationDetails";
	}
}
