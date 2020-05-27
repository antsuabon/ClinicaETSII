package org.springframework.clinicaetsii.web.e2e.doctor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.clinicaetsii.repository.ConstantRepository;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.repository.DiagnosisRepository;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
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
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private ExaminationRepository examinationRepository;

	@Autowired
	private ConstantRepository constantRepository;

	@Autowired
	private ConsultationRepository consultationRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public void clearAppointments() {
		try {
			for (Consultation consultation : this.consultationRepository.findAll()) {
				consultation.setConstants(null);
				consultation.setExaminations(null);
				consultation.setDiagnoses(null);

				this.consultationRepository.save(consultation);
				this.consultationRepository.delete(consultation);

			}

			this.constantRepository.deleteAll();
			this.examinationRepository.deleteAll();
			this.diagnosisRepository.deleteAll();
			this.prescriptionRepository.deleteAll();
			this.appointmentRepository.deleteAll();



		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
