
package org.springframework.clinicaetsii.web.patient;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.validator.PatientFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.Data;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class PatientPatientController {

	private final String VIEWS_PATIENT_CREATE_OR_UPDATE_FORM = "patient/updatePatientForm";

	private final DoctorService doctorService;
	private final PatientService patientService;


	@Autowired
	public PatientPatientController(final PatientService patientService,
			final DoctorService doctorService) {
		this.patientService = patientService;
		this.doctorService = doctorService;
	}

	@InitBinder
	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		dataBinder.setValidator(new PatientFormValidator(this.patientService));
	}

	@ModelAttribute("doctors")
	public Collection<Doctor> populateDoctors() {
		return this.doctorService.findAllDoctorsAndServices();
	}

	@GetMapping("/patient")
	public ModelAndView showPatient() {
		ModelAndView mav = new ModelAndView("patient/patientDetails");
		mav.addObject(this.patientService.findCurrentPatient());
		return mav;
	}


	@Data
	public class PatientForm {
		@Valid
		private Patient patient;
		private String newPassword;
		private String repeatPassword;
	}

	@GetMapping(value = "/patient/edit")
	public String initUpdatePatientForm(final Model model) {
		Patient patientToUpdate = this.patientService.findCurrentPatient();

		PatientForm patientForm = new PatientForm();
		patientForm.setPatient(patientToUpdate);

		model.addAttribute(patientForm);
		return "/patient/updatePatientForm";
	}

	@PostMapping(value = "/patient/edit")
	public String processUpdatePatientForm(@Valid final PatientForm patientForm,
			final BindingResult result) {

		Patient patientToUpdate = this.patientService.findCurrentPatient();
		String oldUsername = String.valueOf(patientToUpdate.getUsername());

		System.out.println(result.getAllErrors());

		if (result.hasErrors()) {

			return "/patient/updatePatientForm";

		} else {

			BeanUtils.copyProperties(patientForm.getPatient(), patientToUpdate, "id", "password",
					"username", "enabled");
			if (patientForm.getNewPassword() != null
					&& !StringUtils.isEmpty(patientForm.getNewPassword())) {
				patientToUpdate.setPassword(patientForm.getNewPassword());
			}

			this.patientService.save(patientToUpdate);

			if (!oldUsername.equals(patientToUpdate.getUsername())) {
				return "redirect:/logout";
			} else {
				return "redirect:/patient";
			}

		}

	}

}
