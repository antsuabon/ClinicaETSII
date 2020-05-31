
package org.springframework.clinicaetsii.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ConstantValidator implements Validator {

	private ConsultationService consultationService;
	private Integer consultationId;


	@Autowired
	public ConstantValidator(final ConsultationService consultationService,
			final Integer consultationId) {
		super();
		this.consultationService = consultationService;
		this.consultationId = consultationId;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return Constant.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		Constant constant = (Constant) target;

		if (constant.getConstantType() == null) {
			errors.rejectValue("constantType", "requiredConstantType", "Este campo es obligatorio");
		} else {

			Consultation consultation =
					this.consultationService.findConsultationById(this.consultationId);
			if (existsConstantTypeInConsultation(constant, consultation)) {
				errors.rejectValue("constantType", "alreadyExistingConstantType",
						"Este tipo de constante ya ha sido registrada en la consulta");
			}
		}

		if (constant.getValue() < 0f) {
			errors.rejectValue("value", "negativeValue",
					"El valor de la constante no debe ser negativo");
		}
	}

	protected boolean existsConstantTypeInConsultation(final Constant constant,
			final Consultation consultation) {
		return consultation != null && consultation.getConstants().stream()
				.anyMatch(c -> constant.getConstantType().equals(c.getConstantType())
						&& !c.getId().equals(constant.getId()));
	}

}
