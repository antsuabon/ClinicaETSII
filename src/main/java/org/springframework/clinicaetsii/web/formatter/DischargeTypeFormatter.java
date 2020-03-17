package org.springframework.clinicaetsii.web.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DischargeTypeFormatter implements Formatter<DischargeType> {

	private ConsultationService consultationService;

	@Autowired
	public DischargeTypeFormatter(final ConsultationService consultationService) {
		this.consultationService = consultationService;
	}

	@Override
	public String print(final DischargeType object, final Locale locale) {
		return object.getName();
	}

	@Override
	public DischargeType parse(final String text, final Locale locale) throws NumberFormatException, ParseException {
		Collection<DischargeType> findDischargeTypes = this.consultationService.findDischargeTypes();
		for (DischargeType dischargeType : findDischargeTypes) {
			if (dischargeType.getId().equals(Integer.valueOf(text))) {
				return dischargeType;
			}
		}
		throw new ParseException("discharge type not found: " + text, 0);
	}

}
