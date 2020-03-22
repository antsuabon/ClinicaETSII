
package org.springframework.clinicaetsii.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MedicineController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class MedicineControllerTests {

	private static final int	TEST_MEDICINE_ID_1	= 1;
	private static final int	TEST_MEDICINE_ID_2	= 2;
	private static final int	TEST_MEDICINE_ID_3	= 3;

	@Autowired
	private MedicineController	MedicineController;

	@MockBean
	private MedicineService		MedicineService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Medicine			medicine1;

	private Medicine			medicine2;

	private Medicine			medicine3;


	void setup() {

		this.medicine1 = new Medicine();
		this.medicine1.setId(MedicineControllerTests.TEST_MEDICINE_ID_1);
		this.medicine1.setCommercialName("Ibuprofeno");
		this.medicine1.setGenericalName("Dalsy");
		this.medicine1.setQuantity(1f);
		this.medicine1.setContraindications("Dolor leve y moderado");
		this.medicine1.setIndications("En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.");

		this.medicine2 = new Medicine();
		this.medicine2.setId(MedicineControllerTests.TEST_MEDICINE_ID_2);
		this.medicine2.setCommercialName("Paracetamol");
		this.medicine2.setGenericalName("Paracel");
		this.medicine2.setQuantity(1f);
		this.medicine2.setContraindications("Dolor y fiebre");
		this.medicine2.setIndications("Paracetamol debe utilizarse con precaución en alcohólicos crónicos y en \n" + "pacientes con deficiencia en glucosa-6 fosfato-deshidrogenasa.");

		this.medicine3 = new Medicine();
		this.medicine3.setId(MedicineControllerTests.TEST_MEDICINE_ID_3);
		this.medicine3.setCommercialName("Arapride");
		this.medicine3.setGenericalName("Omeprazol");
		this.medicine3.setQuantity(1f);
		this.medicine3.setIndications("Debe utilizarse con precaución durante el embarazo y lactancia.");
		this.medicine3.setContraindications("Ulcera duodenal o gástrica");

		List<Medicine> medicines = new ArrayList<>();

		medicines.add(this.medicine1);
		medicines.add(this.medicine2);
		medicines.add(this.medicine3);

		Collection<Medicine> medicines2 = medicines;

		BDDMockito.given(this.MedicineService.findAllMedicines()).willReturn(medicines2);

	}

	@WithMockUser(value = "spring")
	@Test
	void testListMedicines() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anonymous/medicines")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("medicines"))
			.andExpect(MockMvcResultMatchers.view().name("/anonymous/medicines/medicinesList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListMedicines() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anonymous/medicines")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("emptylist"))
			.andExpect(MockMvcResultMatchers.view().name("/anonymous/medicines/medicinesList"));
	}

}
