package org.springframework.clinicaetsii.web.e2e.doctor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class DoctorExaminationControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testShowExaminationPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/doctor/patients/{patientId}/consultations/{consultationId}/examinations/{examinationId}",
				4, 1, 1)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("examination"))
				.andExpect(MockMvcResultMatchers.view()
						.name("doctor/examinations/examinationDetails"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testNotShowExaminationPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/doctor/patients/{patientId}/consultations/{consultationId}/examinations/{examinationId}",
				4, 1, -1)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
				.andExpect(MockMvcResultMatchers.view()
						.name("doctor/examinations/examinationDetails"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void doctorShouldInitExaminationCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(
				"/doctor/patients/{patientId}/consultations/{consultationId}/examinations/new", 4,
				1)).andExpect(MockMvcResultMatchers.model().attributeExists("examination"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/examinations/createExaminationForm"));
	}


	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldProcessCreateExamination() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(
				"/doctor/patients/{patientId}/consultations/{consultationId}/examinations/new", 4,
				1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "asdf"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldNotProcessCreateExamination() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(
				"/doctor/patients/{patientId}/consultations/{consultationId}/examinations/new", 4,
				1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("examination"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("examination",
						"description"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/examinations/createExaminationForm"));

	}


}
