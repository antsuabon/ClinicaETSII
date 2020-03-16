package org.springframework.clinicaetsii.web.doctor;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MedicineFormatter implements Formatter<Medicine> {
	
	private final MedicineService medicineService;
	
	public MedicineFormatter(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Override
	public String print(Medicine object, Locale locale) {
		return object.getId() + "-" + object.getCommercialName() + "(" + object.getGenericalName() + ")";
	}

	@Override
	public Medicine parse(String text, Locale locale) throws ParseException {
		String medicineId = text.split("-")[0];
		int id = Integer.valueOf(medicineId).intValue();
		return this.medicineService.findAllMedicines().stream()
				.filter(x -> x.getId() == id)
				.findFirst().orElse(null);
	}

}
