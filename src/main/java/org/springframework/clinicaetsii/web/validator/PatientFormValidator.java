package org.springframework.clinicaetsii.web.validator;

import java.time.LocalDate; 
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.patient.PatientPatientController.PatientForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PatientFormValidator implements Validator {

	private Pattern dniPattern = Pattern.compile("^[0-9]{8}[A-Z]{1}$");
	private Pattern phonePattern =
			Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
	private Pattern emailPattern = Pattern.compile(".+@.+..+");
	private Pattern passwordPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
	private Pattern nssPattern = Pattern.compile("^\\d{11}$");
	private Pattern datePattern = Pattern.compile("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$");

	private PatientService patientService;

	@Autowired
	public PatientFormValidator(final PatientService patientService) {
		this.patientService = patientService;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		PatientForm patientForm = (PatientForm) target;
		Patient patient = patientForm.getPatient();

		if (patient.getName() == null || StringUtils.isEmpty(patient.getName())) {
			errors.rejectValue("patient.name", "Este campo es obligatorio",
					"Este campo es obligatorio");
		}

		if (patient.getSurname() == null || StringUtils.isEmpty(patient.getSurname())) {
			errors.rejectValue("patient.surname", "Este campo es obligatorio",
					"Este campo es obligatorio");
		}

		if (patient.getState() == null || StringUtils.isEmpty(patient.getState())) {
			errors.rejectValue("patient.state", "Este campo es obligatorio",
					"Este campo es obligatorio");
		}

		if (patient.getAddress() == null || StringUtils.isEmpty(patient.getAddress())) {
			errors.rejectValue("patient.address", "Este campo es obligatorio",
					"Este campo es obligatorio");
		}

		if (patient.getBirthDate() == null || StringUtils.isEmpty(patient.getBirthDate())) {
			errors.rejectValue("patient.birthDate", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (patient.getBirthDate().isAfter(LocalDate.now())) {
			errors.rejectValue("patient.birthDate",
					"El día de nacimiento debe ser anterior al día de hoy",
					"El día de nacimiento debe ser anterior al día de hoy");
		}

		if (patient.getDni() == null || StringUtils.isEmpty(patient.getDni())) {
			errors.rejectValue("patient.dni", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (!this.dniPattern.matcher(patient.getDni()).matches()) {
			errors.rejectValue("patient.dni", "El campo debe seguir el formato: 12345678A",
					"El campo debe seguir el formato: 12345678A");
		}

		if (patient.getEmail() == null || StringUtils.isEmpty(patient.getEmail())) {
			errors.rejectValue("patient.email", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (!this.emailPattern.matcher(patient.getEmail()).matches()) {
			errors.rejectValue("patient.email", "No se ha introducido un Email adecuado",
					"No se ha introducido un Email adecuado");
		}

		if (patient.getPhone() == null || StringUtils.isEmpty(patient.getPhone())) {
			errors.rejectValue("patient.phone", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (!this.phonePattern.matcher(patient.getPhone()).matches()) {
			errors.rejectValue("patient.phone",
					"No se ha introducido un número de teléfono adecuado",
					"No se ha introducido un número de teléfono adecuado");
		}

		if (!(patient.getPhone2() != null || StringUtils.isEmpty(patient.getPhone2()))) {
			if (!this.phonePattern.matcher(patient.getPhone2()).matches()) {
				errors.rejectValue("patient.phone2",
						"No se ha introducido un número de teléfono adecuado",
						"No se ha introducido un número de teléfono adecuado");
			}
		}

		if (patient.getNss() == null || StringUtils.isEmpty(patient.getNss())) {
			errors.rejectValue("patient.nss", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (!this.nssPattern.matcher(patient.getNss()).matches()) {
			errors.rejectValue("patient.nss", "Este campo debe de estar formado por 11 dígitos",
					"Este campo debe de estar formado por 11 dígitos");
		}


		if (patientForm.getNewPassword() != null
				&& !StringUtils.isEmpty(patientForm.getNewPassword())) {

			if (!this.passwordPattern.matcher(patientForm.getNewPassword()).matches()) {
				errors.rejectValue("newPassword",
						"La contraseña debe tener entre 6 y 20 caracteres y debe contener al menos un caracter en minúscula, un caracter en mayúscula y un dígito",
						"La contraseña debe tener entre 6 y 20 caracteres y debe contener al menos un caracter en minúscula, un caracter en mayúscula y un dígito");
			}

			if (!patientForm.getNewPassword().equals(patientForm.getRepeatPassword())) {
				errors.rejectValue("repeatPassword", "La contraseña no coincide",
						"La contraseña no coincide");
			}
		}

		Patient oldPatient = this.patientService.findCurrentPatient();
		if (!oldPatient.getUsername().equals(patient.getUsername())) {
			if (this.patientService.findPatientByUsername(patient.getUsername()) != null) {
				errors.rejectValue("patient.username",
						"Ya existe un usuario con este nombre de usuario",
						"Ya existe un usuario con este nombre de usuario");
			}
		}

		if (patient.getGeneralPractitioner() == null) {
			errors.rejectValue("patient.generalPractitioner", "Un médico de cabecera debe ser asignado", "Un médico de cabecera debe ser asignado");
		
		} else if
			(this.patientService.findAllPatientsFromDoctor(patient.getGeneralPractitioner().getId()).size() >= 5) {
			errors.rejectValue("patient.generalPractitioner", "too_many_patients",
					"Este doctor tiene 5 pacientes asignados");
		}
	}

}
