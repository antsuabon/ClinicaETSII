package org.springframework.clinicaetsii.web.e2e.patient;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PatientPrescriptionControllerE2ETests {

	private static final int TEST_PRESCRIPTION_ID_1 = 1;
	private static final int TEST_PRESCRIPTION_ID_2 = 2;

	private static final int TEST_MEDICINE_ID_1 = 1;
	private static final int TEST_MEDICINE_ID_2 = 2;

	private static final int TEST_PATIENT_ID_1 = 4;

	private static final int TEST_DOCTOR_ID_1 = 1;

	private static final int TEST_SERVICE_ID_1 = 2;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "patient1", authorities = "patient")
	@Test
	void testListPrescritionsByUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescriptions"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/prescriptions/prescriptionList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListPrescritionsByOtherUserRole() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions"))
				.andExpect(status().is(403));
	}

	@WithMockUser(value = "patient1", authorities = "patient")
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void testNotListPrescritionsByUser() throws Exception {

		clearPrescriptions();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptylist"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/prescriptions/prescriptionList"));
	}

	@WithMockUser(value = "patient1", authorities = "patient")
	@Test
	void testShowPrescriptionById() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescription"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/prescriptions/prescriptionDetails"));
	}

	@WithMockUser(value = "patient1", authorities = "patient")
	@Test
	void testNotShowPrescriptionById() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions/-1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/prescriptions/prescriptionDetails"));
	}

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	public void clearPrescriptions() {
		try {
			this.prescriptionRepository.deleteAll();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
