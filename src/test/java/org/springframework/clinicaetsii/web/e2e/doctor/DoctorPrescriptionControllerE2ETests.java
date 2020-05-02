
package org.springframework.clinicaetsii.web.e2e.doctor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class DoctorPrescriptionControllerE2ETests {



	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "doctor1", authorities = "doctor")
	@Test
	void testListPrescriptions() throws Exception {
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/doctor/patients/{patientId}/prescriptions", 4))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescriptions"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/prescriptions/prescriptionsList"));
	}

	@WithMockUser(username = "doctor1", authorities = "doctor")
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void testNotListPrescriptions() throws Exception {
		clearPrescriptions();
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.get("/doctor/patients/{patientId}/prescriptions", 4))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/prescriptions/prescriptionsList"));
	}

	@WithMockUser(username = "doctor1", authorities = "doctor")
	@Test
	void showPrescriptionDetails() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/doctor/patients/{patientId}/prescriptions/{prescriptionId}", 1, 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescription"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/prescriptions/prescriptionDetails"));
	}

	@WithMockUser(username = "doctor1", authorities = "doctor")
	@Test
	void notShowPrescriptionDetails() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/doctor/patients/{patientId}/prescriptions/{prescriptionId}", 1, -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/prescriptions/prescriptionDetails"));
	}
	//
	// @WithMockUser(value = "spring")
	// @Test
	// void deletePrescription() throws Exception {
	// Map<String, Object> model = new HashMap<>();
	// this.doctorPrescriptionController.initDelete(1, model);
	// Mockito.verify(this.prescriptionService).deletePrescription(this.prescription1);
	// }
	//
	// @WithMockUser(value = "spring")
	// @Test
	// void notDeletePrescription() throws Exception {
	// Map<String, Object> model = new HashMap<>();
	// this.doctorPrescriptionController.initDelete(-1, model);
	// Mockito.verify(this.prescriptionService).deletePrescription(null);
	//
	// }

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldInitCreatePrescriptionForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/doctor/patients/{patientId}/prescriptions/new", 4))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescription"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/prescriptions/createPrescriptionForm"));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void shouldProcessCreatePrescriptionForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/prescriptions/new", 4)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("dosage", "3")
						.param("days", "3").param("pharmaceuticalWarning", "None")
						.param("patientWarning", "None").param("medicine", "1"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view()
						.name("redirect:/doctor/patients/{patientId}/prescriptions"));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldNotProcessCreatePrescriptionForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/prescriptions/new", 4)
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("dosage", "")
						.param("days", "").param("pharmaceuticalWarning", "")
						.param("patientWarning", "").param("medicine", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescription"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("prescription",
						"dosage", "days", "medicine"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/prescriptions/createPrescriptionForm"));
	}



	@Autowired
	private PrescriptionRepository prescriptionRepository;

	public void clearPrescriptions() {
		try {

			this.prescriptionRepository.deleteAll();


		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
