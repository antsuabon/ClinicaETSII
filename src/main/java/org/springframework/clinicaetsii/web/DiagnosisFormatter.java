package org.springframework.clinicaetsii.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DiagnosisFormatter implements Formatter<Diagnosis> {

	private ConsultationService consultationService;

	@Autowired
	public DiagnosisFormatter(final ConsultationService consultationService) {
		this.consultationService = consultationService;
	}

	@Override
	public String print(final Diagnosis object, final Locale locale) {
		return object.getName();
	}

	@Override
	public Diagnosis parse(final String text, final Locale locale) throws NumberFormatException, ParseException {
		Collection<Diagnosis> diagnoses = this.consultationService.findAllDiagnoses();
		for (Diagnosis diagnosis : diagnoses) {
			if (diagnosis.getId().equals(Integer.valueOf(text))) {
				return diagnosis;
			}
		}
		throw new ParseException("diagnosis not found: " + text, 0);
	}

}
