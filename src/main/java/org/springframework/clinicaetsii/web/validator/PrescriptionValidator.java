package org.springframework.clinicaetsii.web.validator;

import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PrescriptionValidator implements Validator {
	

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Prescription presc = (Prescription) target;
		
		if(presc.getDosage() <= 0f) {
			errors.rejectValue("dosage", "no_dosage", "La dosis no debe ser cero ni negativo");
		} else if(presc.getDosage() > 24*presc.getDays()) {
			errors.rejectValue("dosage", "not_enough_dosage", "En el plazo de la prescripción debe consumirse al menos una dosis");
		}
		if(presc.getMedicine() == null) {
			errors.rejectValue("medicine", "no_medicine", "En la prescripción debe asignarse un medicamento");
		}
		if(presc.getDays() <= 0) {
			errors.rejectValue("days", "no_days", "El número de días no debe ser cero ni negativo");
		}
			
	}

}
