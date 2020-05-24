
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

	@Test
	@Transactional
	void shouldCreateMedicine() {
		Medicine medicine = new Medicine();
		medicine.setId(4);
		medicine.setCommercialName("Frenadol");
		medicine.setGenericalName("Frenadol Complex");
		medicine.setIndications("Fiebre y dolor de garganta");
		medicine.setContraindications("Tomar cada 8 horas");
		medicine.setQuantity(1f);

		this.medicineService.saveMedicine(medicine);
		medicine = this.medicineService.findMedicineById(4);

		Assertions.assertThat(medicine).isNotNull();
		Assertions.assertThat(medicine.getCommercialName()).isEqualTo("Frenadol");
		Assertions.assertThat(medicine.getGenericalName()).isEqualTo("Frenadol Complex");
		Assertions.assertThat(medicine.getIndications()).isEqualTo("Fiebre y dolor de garganta");
		Assertions.assertThat(medicine.getContraindications()).isEqualTo("Tomar cada 8 horas");
		Assertions.assertThat(medicine.getQuantity()).isEqualTo(1f);
	}

	@Test
	void shouldDeleteMedicine() {
		int medicineId1 = 3;
		Medicine medicine1 = this.medicineService.findMedicineById(medicineId1);
		this.medicineService.deleteMedicine(medicine1);
		Medicine medicineDeleted = this.medicineService.findMedicineById(medicineId1);
		System.out.println(medicineDeleted);
		Assertions.assertThat(medicineDeleted).isEqualTo(null);
	}

}
