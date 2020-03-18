package org.springframework.clinicaetsii.web.patient;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;

@Controller
@RequestMapping("/patient")
public class PatientProfileController {

	private PatientService patientService;

	@Autowired
	public PatientProfileController(final PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public String show(final Map<String, Object> model) {

		Patient results = this.patientService.findPatient();
		model.put("patient", results);

		return "patients/patientDetails";
	}

	@InitBinder
	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new PatientValidator(this.patientService));
	}

	@Data
	public class PatientForm {
		@Valid
		private Patient patient;
		private String newPassword;
		private String repeatPassword;
	}

	@GetMapping(value = "/edit")
	public String initUpdatePatientForm(final Model model) {
		Patient patientToUpdate = this.patientService.findPatient();

		PatientForm patientForm = new PatientForm();
		patientForm.setPatient(patientToUpdate);

		model.addAttribute(patientForm);
		return  "/patients/updatePatientForm";
	}

	@PostMapping(value = "/edit")
	public String processUpdatePatientForm(@Valid final PatientForm patientForm, final BindingResult result) {

		Patient patientToUpdate = this.patientService.findPatient();
		String oldUsername = String.valueOf(patientToUpdate.getUsername());

		System.out.println(result.getAllErrors());

		if (result.hasErrors()) {

			return "/patients/updatePatientForm";

		} else {

			BeanUtils.copyProperties(patientForm.getPatient(), patientToUpdate, "id", "password", "username", "enabled","generalPractitioner");
			if (patientForm.getNewPassword() != null && !StringUtils.isEmpty(patientForm.getNewPassword())) {
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
