package org.springframework.clinicaetsii.web.e2e.admin;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.security.test.context.support.WithMockUser;
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

		clearPatients();

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
	private ConsultationRepository consultationRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public void clearPatients() {
		try {

			this.prescriptionRepository.deleteAll();
			this.consultationRepository.deleteAll();
			this.appointmentRepository.deleteAll();
			this.patientRepository.deleteAll();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
