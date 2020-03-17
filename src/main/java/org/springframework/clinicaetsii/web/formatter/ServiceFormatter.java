package org.springframework.clinicaetsii.web.formatter;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class ServiceFormatter implements Formatter<Service> {

	private DoctorService doctorService;

	@Autowired
	public ServiceFormatter(final DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Override
	public String print(final Service object, final Locale locale) {
		return object.getName();
	}

	@Override
	public Service parse(final String text, final Locale locale) throws NumberFormatException, ParseException {
		Collection<Service> allServices = this.doctorService.findAllServices();

		for (Service service : allServices) {
			if (service.getId().equals(Integer.valueOf(text))) {
				return service;
			}
		}
		throw new ParseException("service not found: " + text, 0);
	}

}
