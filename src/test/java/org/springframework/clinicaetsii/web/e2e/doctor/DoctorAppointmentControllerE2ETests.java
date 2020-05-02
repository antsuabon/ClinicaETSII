package org.springframework.clinicaetsii.web.e2e.doctor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataAppointmentRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataConstantRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataConsultationRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataDiagnosisRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataExaminationRepository;
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
public class DoctorAppointmentControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	@WithMockUser(username = "doctor1", authorities = "doctor")
	void doctorShouldListAppointments() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/appointments"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointments"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/appointmentsList"));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = "doctor")
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void doctorShouldListEmptyAppointments() throws Exception {

		clearAppointments();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/appointments"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/appointmentsList"));
	}

	@Test
	@WithMockUser(value = "spring")
	void anyOtherUserShouldNotListAppointments() throws Exception {


		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/appointments"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}


	@Autowired
	private SpringDataDiagnosisRepository springDataDiagnosisRepository;

	@Autowired
	private SpringDataExaminationRepository springDataExaminationRepository;

	@Autowired
	private SpringDataConstantRepository springDataConstantRepository;

	@Autowired
	private SpringDataConsultationRepository springDataConsultationRepository;

	@Autowired
	private SpringDataPrescriptionRepository springDataPrescriptionRepository;

	@Autowired
	private SpringDataAppointmentRepository springDataAppointmentRepository;

	public void clearAppointments() {
		try {
			for (Consultation consultation : this.springDataConsultationRepository.findAll()) {
				consultation.setConstants(null);
				consultation.setExaminations(null);
				consultation.setDiagnoses(null);

				this.springDataConsultationRepository.save(consultation);
				this.springDataConsultationRepository.delete(consultation);

			}

			this.springDataConstantRepository.deleteAll();
			this.springDataExaminationRepository.deleteAll();
			this.springDataDiagnosisRepository.deleteAll();
			this.springDataPrescriptionRepository.deleteAll();
			this.springDataAppointmentRepository.deleteAll();



		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
