package org.springframework.clinicaetsii.web.doctor;

import java.util.regex.Pattern;

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.web.doctor.DoctorDoctorController.DoctorForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DoctorValidator implements Validator {

	private Pattern dniPattern = Pattern.compile("^[0-9]{8}[A-Z]{1}$");
	private Pattern phonePattern = Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
	private Pattern emailPattern = Pattern.compile(".+@.+..+");
	private Pattern passwordPattern = Pattern.compile("((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		DoctorForm doctorForm = (DoctorForm) target;
		Doctor doctor = doctorForm.getDoctor();

		System.out.println(this.emailPattern.toString());

		if (doctor.getName() == null || StringUtils.isEmpty(doctor.getName())) {
			errors.rejectValue("doctor.name", "Este campo es obligatorio",  "Este campo es obligatorio");
		}

		if (doctor.getSurname() == null || StringUtils.isEmpty(doctor.getSurname())) {
			errors.rejectValue("doctor.surname", "Este campo es obligatorio",  "Este campo es obligatorio");
		}

		if (doctor.getDni() == null || StringUtils.isEmpty(doctor.getDni())) {
			errors.rejectValue("doctor.dni", "Este campo es obligatorio",  "Este campo es obligatorio");
		} else if (!this.dniPattern.matcher(doctor.getDni()).matches()) {
			errors.rejectValue("doctor.dni", "La expresión regular no coincide",  "La expresión regular no coincide");
		}

		if (doctor.getEmail() == null || StringUtils.isEmpty(doctor.getEmail())) {
			errors.rejectValue("doctor.email", "Este campo es obligatorio",  "Este campo es obligatorio");
		} else if (!this.emailPattern.matcher(doctor.getEmail()).matches()) {
			errors.rejectValue("doctor.email", "La expresión regular no coincide",  "La expresión regular no coincide");
		}

		if (doctor.getPhone() == null || StringUtils.isEmpty(doctor.getPhone())) {
			errors.rejectValue("doctor.phone", "Este campo es obligatorio",  "Este campo es obligatorio");
		} else if (!this.phonePattern.matcher(doctor.getPhone()).matches()) {
			errors.rejectValue("doctor.phone", "La expresión regular no coincide",  "La expresión regular no coincide");
		}

		if (doctorForm.getNewPassword() != null && !StringUtils.isEmpty(doctorForm.getNewPassword())) {

			if (!this.passwordPattern.matcher(doctorForm.getNewPassword()).matches()) {
				errors.rejectValue("newPassword", "La contraseña es demasiado débil",  "La contraseña es demasiado débil");
			}

			if (!doctorForm.getNewPassword().equals(doctorForm.getRepeatPassword())) {
				errors.rejectValue("repeatPassword", "La contraseña no coincide",  "La contraseña no coincide");
			}
		}
	}

}
