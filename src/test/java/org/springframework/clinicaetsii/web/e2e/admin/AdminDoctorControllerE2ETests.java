
package org.springframework.clinicaetsii.web.e2e.admin;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataConstantRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataConsultationRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataDiagnosisRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataDoctorRepository;
import org.springframework.clinicaetsii.repository.springdatajpa.SpringDataExaminationRepository;
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

		this.clearDoctors();

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
	private SpringDataPatientRepository springDataPatientRepository;

	@Autowired
	private SpringDataDoctorRepository springDataDoctorRepository;

	public void clearDoctors() {
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

			for (Patient patient : this.springDataPatientRepository.findAll()) {
				patient.setGeneralPractitioner(null);

				this.springDataPatientRepository.save(patient);
			}

			this.springDataDoctorRepository.deleteAll();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
