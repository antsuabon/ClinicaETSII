
package org.springframework.clinicaetsii.web.administrative;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = AdministrativeMedicineController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class AdministrativeMedicineControllerTests {

	private static final int	TEST_MEDICINE_ID_1	= 1;
	private static final int	TEST_MEDICINE_ID_2	= 2;

	@Autowired
	private AdministrativeMedicineController administrativeMedicineController;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MedicineService		medicineService;


	@MockBean
	private PrescriptionService		prescriptionService;



	private Medicine medicine1;

	private Medicine medicine2;

	@BeforeEach
	void setup() {

		this.medicine1 = new Medicine();
		this.medicine1.setId(5);
		this.medicine1.setCommercialName("Frenadol");
		this.medicine1.setGenericalName("Frenadol Complex");
		this.medicine1.setIndications("Fiebre y dolor de garganta");
		this.medicine1.setContraindications("Tomar cada 8 horas");
		this.medicine1.setQuantity(1f);

		this.medicine2 = new Medicine();
		this.medicine2.setId(2);
		this.medicine2.setCommercialName("Aspirina");
		this.medicine2.setGenericalName("Ácido acetilsalicílico ");
		this.medicine2.setIndications("Dolor de cabeza");
		this.medicine2.setContraindications("Tomar cada 8 horas");
		this.medicine2.setQuantity(1f);

		List<Medicine> medicines = new ArrayList<>();
		medicines.add(this.medicine1);
		medicines.add(this.medicine2);

		BDDMockito.given(this.medicineService.findAllMedicines()).willReturn(medicines);
		BDDMockito.given(this.medicineService.findMedicineById(5)).willReturn(this.medicine1);
		BDDMockito.given(this.prescriptionService.findPrescriptionByMedicineId(5)).willReturn(null);
	}

	@Test
	@WithMockUser(username = "administrative1", roles = "administrative")
	void administrativeShouldListMedicines() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/medicines")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("medicines"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/medicine/medicinesList"));
	}

	@Test
	@WithMockUser(username = "administrative1", roles = "administrative")
	void administrativeShouldNotListMedicines() throws Exception {
		BDDMockito.given(this.medicineService.findAllMedicines()).willReturn(new ArrayList<>());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/medicines")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptylist"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/medicine/medicinesList"));
	}


	@WithMockUser(value = "spring")
	@Test
	void deleteMedicine() throws Exception {
		this.administrativeMedicineController.initDelete(5);
		Mockito.verify(this.medicineService).deleteMedicine(this.medicine1);
	}

	@WithMockUser(value = "spring")
	@Test
	void notDeleteMedicine() throws Exception {
		this.administrativeMedicineController.initDelete(-1);
		Mockito.verify(this.medicineService).deleteMedicine(null);
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowMedicine() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}", 5))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("medicine"))
				.andExpect(MockMvcResultMatchers.view().name("administrative/medicine/medicineDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotShowMedicine() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}", -1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
				.andExpect(MockMvcResultMatchers.view().name("administrative/medicine/medicineDetails"));
	}

	@Test
	@WithMockUser(username = "administrative1", roles = "administrative")
	void shouldInitEditMedicineForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrative/medicines/{medicineId}/edit",5))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("medicine"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/medicine/updateMedicineForm"));
	}




	@Test
	@WithMockUser(username = "administrative1", roles = "administrative")
	void shouldProcessEditMedicineForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/administrative/medicines/{medicineId}/edit",5).with(SecurityMockMvcRequestPostProcessors.csrf())
								.param("commercialName", "F")
								.param("genericalName", "F C")
								.param("indications", "None")
								.param("contraindications", "None")
								.param("quantity", "1"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/administrative/medicines/{medicineId}"));
	}

	@Test
	@WithMockUser(username = "administrative1", roles = "administrative")
	void shouldNotProcessEditMedicineForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/administrative/medicines/{medicineId}/edit",5).with(SecurityMockMvcRequestPostProcessors.csrf())
								.param("commercialName", "")
								.param("genericalName", "")
								.param("indications", "None")
								.param("contraindications", "None")
								.param("quantity", "-1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("medicine"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("medicine", "commercialName"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("medicine", "genericalName"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("medicine", "quantity"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/medicine/updateMedicineForm"));
	}


































}
