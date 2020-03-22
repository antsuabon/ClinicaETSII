package org.springframework.clinicaetsii.model.form;

import javax.validation.Valid;

import org.springframework.clinicaetsii.model.Doctor;

import lombok.Data;

@Data
public class DoctorForm {
	@Valid
	private Doctor doctor;
	private String newPassword;
	private String repeatPassword;
}
