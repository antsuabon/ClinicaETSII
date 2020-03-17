package org.springframework.clinicaetsii.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.service.ConstantService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class ConstantTypeFormatter implements Formatter<ConstantType> {

	private ConstantService constantService;

	@Autowired
	public ConstantTypeFormatter(final ConstantService constantService) {
		this.constantService = constantService;
	}

	@Override
	public String print(final ConstantType object, final Locale locale) {
		return object.getName();
	}

	@Override
	public ConstantType parse(final String text, final Locale locale) throws NumberFormatException, ParseException {
		Collection<ConstantType> constantTypes = this.constantService.findAllConstantTypes();

		for (ConstantType constantType : constantTypes) {
			if (constantType.getId().equals(Integer.valueOf(text))) {
				return constantType;
			}
		}
		throw new ParseException("constant type not found: " + text, 0);
	}

}
