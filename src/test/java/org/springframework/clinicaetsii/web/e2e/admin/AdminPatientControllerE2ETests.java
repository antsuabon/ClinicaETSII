package org.springframework.clinicaetsii.web.e2e.admin;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataAppointmentRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataConsultationRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataPatientRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataPrescriptionRepository;
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
public class AdminPatientControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "admin", authorities = {"admin"})
	void shouldListPatients() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/patients/patientsList"));
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@WithMockUser(username = "admin", authorities = {"admin"})
	void shouldNotListPatients() throws Exception {

		this.clearPatients();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/patients/patientsList"));
	}


	@Test
	@WithMockUser(username = "admin", authorities = {"admin"})
	void shouldShowPatientDetails() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}", 4))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/admin/patients/patientDetails"));

	}

	@Test
	@WithMockUser(username = "admin", authorities = {"admin"})
	void shouldNotShowPatientDetails() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}", -2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void shouldDeleteDoctor() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}/delete", 11))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/patients"));
	}

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void shouldNotDeleteDoctorWithConsultations() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}/delete", 1))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/patients"));
	}

	@Autowired
	private SpringDataConsultationRepository springDataConsultationRepository;

	@Autowired
	private SpringDataPrescriptionRepository springDataPrescriptionRepository;

	@Autowired
	private SpringDataPatientRepository springDataPatientRepository;

	@Autowired
	private SpringDataAppointmentRepository springDataAppointmentRepository;

	public void clearPatients() {
		try {

			this.springDataPrescriptionRepository.deleteAll();
			this.springDataConsultationRepository.deleteAll();
			this.springDataAppointmentRepository.deleteAll();
			this.springDataPatientRepository.deleteAll();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
