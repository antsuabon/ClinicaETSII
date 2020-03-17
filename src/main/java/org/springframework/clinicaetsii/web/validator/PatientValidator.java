package org.springframework.clinicaetsii.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PatientValidator implements Validator {

	private PatientService patientService;

	@Autowired
	public PatientValidator(final PatientService patientService) {
		this.patientService = patientService;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		Patient patient = (Patient) target;

		if (this.patientService.findAllPatientsFromDoctor(patient.getGeneralPractitioner().getId()).size() >= 1000) {
			errors.rejectValue("generalPractitioner", "too_many_patients", "Este doctor tiene 1000 pacientes asignados");
		}

	}

}
