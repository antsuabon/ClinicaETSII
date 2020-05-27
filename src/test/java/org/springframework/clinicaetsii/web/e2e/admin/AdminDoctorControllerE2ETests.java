
package org.springframework.clinicaetsii.web.e2e.admin;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.ConstantRepository;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.repository.DiagnosisRepository;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
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
public class AdminDoctorControllerE2ETests {


	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	void testListDoctors() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctors"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/doctors/doctorsList"));
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	void testNotListDoctors() throws Exception {

		clearDoctors();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/doctors/doctorsList"));
	}

	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	void testFindDoctorById() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/doctors/doctorDetails"));
	}

	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	void shouldNotShowDoctor() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}", -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void shouldDeleteDoctor() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}/delete", 11))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/doctors"));
	}

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void shouldNotDeleteDoctorWithConsultations() throws Exception {


		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}/delete", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void shouldNotDeleteDoctorWithPrescriptions() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}/delete", 1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
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
	private PatientRepository patientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	public void clearDoctors() {
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

			for (Patient patient : this.patientRepository.findAll()) {
				patient.setGeneralPractitioner(null);

				this.patientRepository.save(patient);
			}

			this.doctorRepository.deleteAll();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
