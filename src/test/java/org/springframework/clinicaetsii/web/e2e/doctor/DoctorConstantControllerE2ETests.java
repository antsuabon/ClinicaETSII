
package org.springframework.clinicaetsii.web.e2e.doctor;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class DoctorConstantControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testInitConstantCreation() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(
						"/doctor/patients/{patientId}/consultations/{consultationId}/constants/new",
						4, 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("constantTypes", not(empty())))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testProcessConstantCreation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new",
						4, 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("constantType", "2")
				.param("value", "20")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testNotProcessConstantCreation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new",
						4, 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("constantType", "-1")
				.param("value", "-5")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("constant"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("constant",
						"constantType", "value"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testInitConstantEdition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit",
				4, 1, 1)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("constantTypes", not(empty())))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void testProcessConstantEdition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(
				"/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit",
				4, 1, 1).with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("constantType", "2").param("value", "20"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testNotProcessConstantEdition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(
				"/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit",
				4, 1, 1).with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("constantType", "5").param("value", "-5"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("constant"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("constant",
						"constantType", "value"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void testProcessConstantDeletion() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/delete",
				4, 1, 1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testProcessNotExistingConstantDeletion() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/delete",
				4, 1, -1)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}
}
