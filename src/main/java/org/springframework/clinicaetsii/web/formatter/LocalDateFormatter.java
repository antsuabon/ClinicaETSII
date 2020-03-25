package org.springframework.clinicaetsii.web.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;


@Component
public class LocalDateFormatter implements Formatter<LocalDate>{

	private static final DateTimeFormatter	FORMATTER				= DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public String print(final LocalDate object, final Locale locale) {
		return LocalDateFormatter.FORMATTER.format(object);
	}

	@Override
	public LocalDate parse(final String text, final Locale locale) throws ParseException {
		if (text.contains("T")) {
			return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
		} else {
			return LocalDate.parse(text, LocalDateFormatter.FORMATTER);
		}
	}
}