package org.springframework.clinicaetsii.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MedicineServiceTests {
	
	@Autowired
	protected MedicineService medicineService;
	
	@Test
	void shouldShowValidPrescription() {
		int prescriptionId = 1;
		Medicine medicine = this.medicineService.findMedicineById(prescriptionId);
		Assertions.assertThat(medicine).isNotNull();
		
		Assertions.assertThat(medicine.getGenericalName()).isNotNull().isNotBlank();
		Assertions.assertThat(medicine.getCommercialName()).isNotNull().isNotBlank();
	}

}
