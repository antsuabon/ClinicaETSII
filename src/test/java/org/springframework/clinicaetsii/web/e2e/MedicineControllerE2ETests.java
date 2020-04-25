
package org.springframework.clinicaetsii.web.e2e;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataMedicineRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataPrescriptionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class MedicineControllerE2ETests {

	private static final int TEST_MEDICINE_ID_1 = 1;
	private static final int TEST_MEDICINE_ID_2 = 2;
	private static final int TEST_MEDICINE_ID_3 = 3;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@WithMockUser(value = "spring")
	@Test
	void testListMedicines() throws Exception {
		this.mockMvc.perform(get("/anonymous/medicines")).andExpect(status().isOk())
				.andExpect(model().attributeExists("medicines"))
				.andExpect(view().name("/anonymous/medicines/medicinesList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testEmptyListMedicines() throws Exception {

		clearMedicines();

		this.mockMvc.perform(get("/anonymous/medicines")).andExpect(status().isOk())
				.andExpect(model().attributeExists("emptylist"))
				.andExpect(view().name("/anonymous/medicines/medicinesList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowMedicine() throws Exception {
		this.mockMvc.perform(get("/anonymous/medicines/{medicineId}", TEST_MEDICINE_ID_1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("medicine"))
				.andExpect(view().name("/medicines/medicineDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotShowMedicine() throws Exception {
		this.mockMvc.perform(get("/anonymous/medicines/{medicineId}", -1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("empty"))
				.andExpect(view().name("/medicines/medicineDetails"));
	}


	@Autowired
	private SpringDataPrescriptionRepository springDataPrescriptionRepository;

	@Autowired
	private SpringDataMedicineRepository springDataMedicineRepository;

	public void clearMedicines() {
		try {
			this.springDataPrescriptionRepository.deleteAll();
			this.springDataMedicineRepository.deleteAll();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
