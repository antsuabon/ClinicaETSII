package org.springframework.clinicaetsii.web.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MedicineFormatter implements Formatter<Medicine> {

	private final MedicineService medicineService;

	public MedicineFormatter(final MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Override
	public String print(final Medicine object, final Locale locale) {
		return object.getCommercialName() + " (" + object.getGenericalName() + ")";
	}

	@Override
	public Medicine parse(final String text, final Locale locale) throws NumberFormatException, ParseException {
		Medicine medicine = this.medicineService.findMedicineById(Integer.valueOf(text));

		if(medicine != null) {
			return medicine;
		} else {
			throw new ParseException("medicine not found: " + text, 0);
		}
	}

}
