
package org.springframework.clinicaetsii.web.patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.validator.PatientValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class PatientPatientController {

	private final String			VIEWS_PATIENT_CREATE_OR_UPDATE_FORM	= "patient/patients/createOrUpdatePatientForm";

	private final DoctorService		doctorService;
	private final PatientService	patientService;


	@Autowired
	public PatientPatientController(final PatientService patientService, final DoctorService doctorService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
	}

	@InitBinder
	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		dataBinder.setValidator(new PatientValidator(this.patientService));
	}

	@ModelAttribute("doctors")
	public Collection<Doctor> populateDoctors() {
		return this.doctorService.findAllDoctors();
	}

	@GetMapping(value = "/patient/edit")
	public String initUpdatePatientForm(final Model model) {
		Patient patient = this.patientService.findCurrentPatient();
		model.addAttribute("patient", patient);
		return this.VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/patient/edit")
	public String processUpdatePatientForm(final Patient patient, final BindingResult result, final Model model, @ModelAttribute(name = "generalPractitioner") final Doctor practitioner) {
		if (result.hasErrors()) {
			return this.VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
		} else {
			Patient patient2 = this.patientService.findCurrentPatient();
			patient2.setGeneralPractitioner(practitioner);
			this.patientService.savePatient(patient2);
			return "redirect:/patient";
		}
	}

}
