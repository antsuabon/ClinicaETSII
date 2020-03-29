
package org.springframework.clinicaetsii.web.validator;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.form.DoctorForm;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DoctorFormValidator implements Validator {

	private Pattern dniPattern = Pattern.compile("^[0-9]{8}[A-Z]{1}$");
	private Pattern phonePattern =
			Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
	private Pattern emailPattern = Pattern.compile(".+@.+..+");
	private Pattern passwordPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
	private Pattern collegiateCodePattern = Pattern.compile("^\\d{9}$");

	private DoctorService doctorService;

	private UserService userService;

	@Autowired
	public DoctorFormValidator(final DoctorService doctorService, final UserService userService) {
		this.doctorService = doctorService;
		this.userService = userService;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		DoctorForm doctorForm = (DoctorForm) target;
		Doctor doctor = doctorForm.getDoctor();

		if (doctor.getName() == null || StringUtils.isEmpty(doctor.getName())) {
			errors.rejectValue("doctor.name", "requiredName", "Este campo es obligatorio");
		}

		if (doctor.getSurname() == null || StringUtils.isEmpty(doctor.getSurname())) {
			errors.rejectValue("doctor.surname", "requiredSurname", "Este campo es obligatorio");
		}

		if (doctor.getDni() == null || StringUtils.isEmpty(doctor.getDni())) {
			errors.rejectValue("doctor.dni", "requiredDni", "Este campo es obligatorio");
		} else if (!this.dniPattern.matcher(doctor.getDni()).matches()) {
			errors.rejectValue("doctor.dni", "invalidDniPattern",
					"El campo debe seguir el formato: 12345678A");
		}

		if (doctor.getEmail() == null || StringUtils.isEmpty(doctor.getEmail())) {
			errors.rejectValue("doctor.email", "requiredEmail", "Este campo es obligatorio");
		} else if (!this.emailPattern.matcher(doctor.getEmail()).matches()) {
			errors.rejectValue("doctor.email", "invalidEmailPattern",
					"No se ha introducido un Email adecuado");
		}

		if (doctor.getPhone() == null || StringUtils.isEmpty(doctor.getPhone())) {
			errors.rejectValue("doctor.phone", "requiredPhone", "Este campo es obligatorio");
		} else if (!this.phonePattern.matcher(doctor.getPhone()).matches()) {
			errors.rejectValue("doctor.phone", "invalidPhonePattern",
					"No se ha introducido un número de teléfono adecuado");
		}

		if (doctor.getCollegiateCode() == null || StringUtils.isEmpty(doctor.getCollegiateCode())) {
			errors.rejectValue("doctor.collegiateCode", "requiredCollegiateCode",
					"Este campo es obligatorio");
		} else if (!this.collegiateCodePattern.matcher(doctor.getCollegiateCode()).matches()) {
			errors.rejectValue("doctor.collegiateCode", "invalidCollegiateCodePattern",
					"Este campo debe de estar formado por 9 dígitos");
		} else {
			if (Integer.valueOf(doctor.getCollegiateCode().substring(0, 2)).equals(0) || Integer
					.valueOf(doctor.getCollegiateCode().substring(0, 2)).compareTo(52) > 0) {
				errors.rejectValue("doctor.collegiateCode", "invalidCollegiateCodePatternFirstPair",
						"La primera pareja de dijitos es erronea");
			}

			if (Integer.valueOf(doctor.getCollegiateCode().substring(2, 4)).equals(0) || Integer
					.valueOf(doctor.getCollegiateCode().substring(2, 4)).compareTo(52) > 0) {
				errors.rejectValue("doctor.collegiateCode",
						"invalidCollegiateCodePatternSecondPair",
						"La segunda pareja de dígitos es erronea");
			}
		}

		if (doctorForm.getNewPassword() != null
				&& !StringUtils.isEmpty(doctorForm.getNewPassword())) {

			if (!this.passwordPattern.matcher(doctorForm.getNewPassword()).matches()) {
				errors.rejectValue("newPassword", "invalidPassword",
						"La contraseña debe tener entre 6 y 20 caracteres y debe contener al menos un caracter en minúscula, un caracter en mayúscula y un dígito");
			}

			if (!doctorForm.getNewPassword().equals(doctorForm.getRepeatPassword())) {
				errors.rejectValue("repeatPassword", "unrepeatedPassword",
						"La contraseña no coincide");
			}
		}

		if (doctor.getUsername() == null || StringUtils.isEmpty(doctor.getUsername())) {
			errors.rejectValue("doctor.username", "emptyUsername", "Este campo es obligatorio");
		}

		Doctor oldDoctor = this.doctorService.findCurrentDoctor();
		if (!oldDoctor.getUsername().equals(doctor.getUsername())) {
			if (this.userService.findUserByUsername(doctor.getUsername()) != null) {
				errors.rejectValue("doctor.username", "alreadyExistingUsername",
						"Ya existe un usuario con este nombre de usuario");
			}
		}


	}

}
