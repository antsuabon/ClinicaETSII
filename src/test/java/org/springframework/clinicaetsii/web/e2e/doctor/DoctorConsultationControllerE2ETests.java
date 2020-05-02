
package org.springframework.clinicaetsii.web.e2e.doctor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
class DoctorConsultationControllerE2ETests {


	@Autowired
	private MockMvc mockMvc;



	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testNotProcessCreationForm() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/consultations/new", 4)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime", "2020-03-07 10:00:00").param("appointmentId", "1")
						.param("anamnesis", "").param("remarks", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("consultation"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("consultation",
						"startTime"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testInitEditionForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(
						"/doctor/patients/{patientId}/consultations/{consultationId}/edit", 4, 1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers
						.view().name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testProcessEditionForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/consultations/{consultationId}/edit", 4,
								1)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime",
								LocalDateTime.now().minusHours(1)
										.format(DateTimeFormatter.ISO_DATE_TIME))
						.param("appointmentId", String.valueOf(1)).param("anamnesis", "PRUEBA")
						.param("remarks", "PRUEBA").param("diagnoses", "1")
						.param("dischargeType", "1"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testProcessEditionFormWithoutDischargeType() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/consultations/{consultationId}/edit", 4,
								1)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime",
								LocalDateTime.now().minusHours(1)
										.format(DateTimeFormatter.ISO_DATE_TIME))
						.param("appointmentId", String.valueOf(1)).param("anamnesis", "PRUEBA")
						.param("remarks", "PRUEBA").param("diagnoses", "1")
						.param("dischargeType", ""))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void testNotProcessEditionForm() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/consultations/{consultationId}/edit", 4,
								3)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime", "2020-03-12 11:00:00").param("appointmentId", "3")
						.param("anamnesis", "PRUEBA").param("remarks", "PRUEBA")
						.param("diagnoses", "1").param("dischargeType", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("consultation"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("consultation",
						"dischargeType"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

}
