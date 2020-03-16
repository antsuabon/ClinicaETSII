package org.springframework.clinicaetsii.web;

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
	public String print(Doctor object, Locale locale) {
		return object.getFullName() + "-" + object.getId();
	}

	@Override
	public Doctor parse(String text, Locale locale) throws ParseException {
		String[] id = text.split("-");
		return this.doctorService.findDoctorById(Integer.valueOf(id[1]));
	}
	
	

}
