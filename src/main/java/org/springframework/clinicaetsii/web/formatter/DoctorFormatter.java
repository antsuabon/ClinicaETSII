package org.springframework.clinicaetsii.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DoctorFormatter implements Formatter<Doctor> {

	@Autowired
	private DoctorService doctorService;

	@Override
	public String print(final Doctor object, final Locale locale) {
		return object.getFullName();
	}

	@Override
	public Doctor parse(final String text, final Locale locale) throws  NumberFormatException, ParseException {
		Doctor doctor = this.doctorService.findDoctorById(Integer.valueOf(text));

		if (doctor != null) {
			return doctor;
		} else {
			throw new ParseException("doctor not found: " + text, 0);
		}

	}



}
