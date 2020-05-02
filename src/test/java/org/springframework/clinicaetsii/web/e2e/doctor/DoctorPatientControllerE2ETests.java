
package org.springframework.clinicaetsii.web.e2e.doctor;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class DoctorPatientControllerE2ETests {


	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "doctor1", authorities = "doctor")
	@Test
	void testListPatients() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
				.andExpect(MockMvcResultMatchers.view().name("/doctor/patientsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListPatientsWithOtherUserRole() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@WithMockUser(username = "doctor1", authorities = "doctor")
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void testNotListPatients() throws Exception {
		clearPatients();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/doctor/patientsList"));
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
