package org.springframework.clinicaetsii.web.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.web.patient.PatientPatientController;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PatientValidator implements Validator {
	Logger log;

	private Pattern dniPattern = Pattern.compile("^[0-9]{8}[A-Z]{1}$");
	private Pattern phonePattern =
			Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");
	private Pattern emailPattern = Pattern.compile(".+@.+..+");
	private Pattern nssPattern = Pattern.compile("^\\d{11}$");

	private PatientService patientService;
	private UserService userService;

	@Autowired
	public PatientValidator(final PatientService patientService, final UserService userService) {
		this.patientService = patientService;
		this.userService = userService;
		log = LoggerFactory.getLogger(PatientValidator.class);
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return Patient.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {

		Patient patient = (Patient) target;

		if (patient.getUsername() == null || StringUtils.isEmpty(patient.getUsername())) {
			errors.rejectValue("username", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (this.userService.findUserByUsername(patient.getUsername()) != null) {
			errors.rejectValue("username", "Este nombre de usuario ya existe",
					"Este nombre de usuario ya existe");
		}

		if (patient.getName() == null || StringUtils.isEmpty(patient.getName())) {
			errors.rejectValue("name", "Este campo es obligatorio", "Este campo es obligatorio");
		}

		if (patient.getSurname() == null || StringUtils.isEmpty(patient.getSurname())) {
			errors.rejectValue("surname", "Este campo es obligatorio", "Este campo es obligatorio");
		}

		if (patient.getState() == null || StringUtils.isEmpty(patient.getState())) {
			errors.rejectValue("state", "Este campo es obligatorio", "Este campo es obligatorio");
		}

		if (patient.getAddress() == null || StringUtils.isEmpty(patient.getAddress())) {
			errors.rejectValue("address", "Este campo es obligatorio", "Este campo es obligatorio");
		}

		if (patient.getBirthDate() == null) {
			errors.rejectValue("birthDate", "Este campo es obligatorio",
					"Este campo es obligatorio");
		} else if (patient.getBirthDate().isAfter(LocalDate.now())) {
			errors.rejectValue("birthDate", "El día de nacimiento debe ser anterior al día de hoy",
					"El día de nacimiento debe ser anterior al día de hoy");
		}

		if (patient.getDni() == null || StringUtils.isEmpty(patient.getDni())) {
			errors.rejectValue("dni", "Este campo es obligatorio", "Este campo es obligatorio");
		} else if (!this.dniPattern.matcher(patient.getDni()).matches()) {
			errors.rejectValue("dni", "El campo debe seguir el formato: 12345678A",
					"El campo debe seguir el formato: 12345678A");
		}

		if (patient.getEmail() == null || StringUtils.isEmpty(patient.getEmail())) {
			errors.rejectValue("email", "Este campo es obligatorio", "Este campo es obligatorio");
		} else if (!this.emailPattern.matcher(patient.getEmail()).matches()) {
			errors.rejectValue("email", "No se ha introducido un Email adecuado",
					"No se ha introducido un Email adecuado");
		}

		if (patient.getPhone() == null || StringUtils.isEmpty(patient.getPhone())) {
			errors.rejectValue("phone", "Este campo es obligatorio", "Este campo es obligatorio");
		} else if (!this.phonePattern.matcher(patient.getPhone()).matches()) {
			errors.rejectValue("phone", "No se ha introducido un número de teléfono adecuado",
					"No se ha introducido un número de teléfono adecuado");
		}

		if (patient.getPhone2() != null && !StringUtils.isEmpty(patient.getPhone2())) {
			if (!this.phonePattern.matcher(patient.getPhone2()).matches()) {
				errors.rejectValue("phone2", "No se ha introducido un número de teléfono adecuado",
						"No se ha introducido un número de teléfono adecuado");
			}
		}

		if (patient.getNss() == null || StringUtils.isEmpty(patient.getNss())) {
			errors.rejectValue("nss", "Este campo es obligatorio", "Este campo es obligatorio");
		} else if (!this.nssPattern.matcher(patient.getNss()).matches()) {
			errors.rejectValue("nss", "Este campo debe de estar formado por 11 dígitos",
					"Este campo debe de estar formado por 11 dígitos");
		}
	
		if(patient.getGeneralPractitioner() == null) {
			errors.rejectValue("generalPractitioner", "Un médico de cabecera debe ser asignado", "Un médico de cabecera debe ser asignado");
			
		} 
		
		else if (this.patientService.findAllPatientsFromDoctor(patient.getGeneralPractitioner().getId())
				.size() >= 5) {
			errors.rejectValue("generalPractitioner", "too_many_patients",
					"Este doctor tiene 5 pacientes asignados");
		}
	}

}
