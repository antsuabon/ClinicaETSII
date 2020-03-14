package org.springframework.clinicaetsii.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class ConstantTypeFormatter implements Formatter<ConstantType> {

	@Override
	public String print(final ConstantType object, final Locale locale) {
		return object.getName();
	}

	@Override
	public ConstantType parse(final String text, final Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
