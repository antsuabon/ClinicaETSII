package org.springframework.clinicaetsii.service;

import java.util.Collection;

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
	void shouldListMedicines() {
		Collection<Medicine> medicines = this.medicineService.findAllMedicines();
		Assertions.assertThat(medicines.size()).isEqualTo(3);

	}

	@Test
	void shouldShowValidPrescription() {
		int prescriptionId = 1;
		Medicine medicine = this.medicineService.findMedicineById(prescriptionId);
		Assertions.assertThat(medicine).isNotNull();

		Assertions.assertThat(medicine.getGenericalName()).isNotNull().isNotBlank();
		Assertions.assertThat(medicine.getCommercialName()).isNotNull().isNotBlank();
	}

	@Test
	void shouldListAllMedicines() {

		Collection<Medicine> medicines = this.medicineService.findAllMedicines();
		Assertions.assertThat(medicines.size()).isEqualTo(3);
	}

}
