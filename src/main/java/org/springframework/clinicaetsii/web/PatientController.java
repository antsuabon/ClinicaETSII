package org.springframework.clinicaetsii.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PatientController {
	
	private PatientService patientService;
	private DoctorService doctorService;
	private static final String VIEWS_PATIENT_CREATE_OR_UPDATE_FORM = "patient/patients/createOrUpdatePatientForm";

	
	@Autowired
	public PatientController(PatientService patientService, DoctorService doctorService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
	}
	
	@GetMapping(value = "/patient/patients/{patientId}/edit")
	public String initUpdatePatientForm(@PathVariable("patientId") int patientId, Model model) {
		Patient patient = this.patientService.findPatient(patientId);
		model.addAttribute("doctors", this.doctorService.findAllDoctors());
		model.addAttribute("patient", patient);
		return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/patient/patients/{patientId}/edit")
	public String processUpdatePatientForm(Patient patient, @PathVariable("patientId") int patientId, BindingResult result, Model model, @ModelAttribute(name = "generalPractitioner") Doctor practitioner) {
		model.addAttribute("doctors", this.doctorService.findAllDoctors());
		/*if (result.hasErrors()) {
			return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
		}*/
		if(this.patientService.findAllPatientFromDoctors(practitioner.getId()).size()>1000) {
			result.rejectValue("generalPractitioner", "too_many_patients", "Este doctor tiene 1000 pacientes asignados");
			return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
		}
		else {
			Patient patient2 = this.patientService.findPatient(patientId);
			patient2.setGeneralPractitioner(practitioner);
			this.patientService.savePatient(patient2);
			return "redirect:/";
		}
	}
	
}
