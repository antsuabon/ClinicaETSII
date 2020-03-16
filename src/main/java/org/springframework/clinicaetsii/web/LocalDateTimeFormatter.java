package org.springframework.clinicaetsii.web;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

	private static final DateTimeFormatter	FORMATTER				= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static final DateTimeFormatter	TO_LOCAL_DATE_TIME_ISO	= DateTimeFormatter.ISO_DATE_TIME;

	@Override
	public String print(final LocalDateTime object, final Locale locale) {
		return LocalDateTimeFormatter.FORMATTER.format(object);
	}

	@Override
	public LocalDateTime parse(final String text, final Locale locale) throws ParseException {
		return LocalDateTime.parse(text, LocalDateTimeFormatter.TO_LOCAL_DATE_TIME_ISO);
	}

}
