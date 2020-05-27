
package org.springframework.clinicaetsii.web.e2e.administrative;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.repository.MedicineRepository;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdministrativeMedicineControllerE2ETests {


	@Autowired
	private MockMvc mockMvc;


	@Test
	@WithMockUser(username = "administrative1", authorities = "administrative")
	void administrativeShouldListMedicines() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/medicines"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("medicines"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/medicine/medicinesList"));
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@WithMockUser(username = "administrative1", authorities = "administrative")
	void administrativeShouldNotListMedicines() throws Exception {
		clearMedicines();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/medicines"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptylist"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/medicine/medicinesList"));
	}

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void deleteMedicine() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}/delete",
						1))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/administrative/medicines"));
	}


	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void testShowMedicine() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("medicine"))
				.andExpect(MockMvcResultMatchers.view()
						.name("administrative/medicine/medicineDetails"));
	}

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void testNotShowMedicine() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}", -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
				.andExpect(MockMvcResultMatchers.view()
						.name("administrative/medicine/medicineDetails"));
	}

	@Test
	@WithMockUser(username = "administrative1", authorities = "administrative")
	void shouldInitEditMedicineForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}/edit",
						1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("medicine"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/medicine/updateMedicineForm"));
	}



	@Test
	@WithMockUser(username = "administrative1", authorities = "administrative")
	void shouldProcessEditMedicineForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/administrative/medicines/{medicineId}/edit", 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("commercialName", "F")
				.param("genericalName", "F C").param("indications", "None")
				.param("contraindications", "None").param("quantity", "1"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view()
						.name("redirect:/administrative/medicines/{medicineId}"));
	}

	@Test
	@WithMockUser(username = "administrative1", authorities = "administrative")
	void shouldNotProcessEditMedicineForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/administrative/medicines/{medicineId}/edit", 1)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("commercialName", "").param("genericalName", "")
						.param("indications", "None").param("contraindications", "None")
						.param("quantity", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("medicine"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("medicine",
						"commercialName"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("medicine",
						"genericalName"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("medicine",
						"quantity"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/medicine/updateMedicineForm"));
	}



	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private PrescriptionRepository prescriptionRepository;

	public void clearMedicines() {
		try {
			this.prescriptionRepository.deleteAll();
			this.medicineRepository.deleteAll();



		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}



}
