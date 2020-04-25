
package org.springframework.clinicaetsii.web.e2e;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DoctorControllerE2ETests {

	private static final int TEST_DOCTOR_ID_1 = 1;
	private static final int TEST_DOCTOR_ID_2 = 2;
	private static final int TEST_DOCTOR_ID_3 = 3;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
	@Test
	void testListDoctors() throws Exception {
		this.mockMvc.perform(get("/anonymous/doctors")).andExpect(status().isOk())
				.andExpect(model().attributeExists("doctors"))
				.andExpect(view().name("/anonymous/doctors/doctorsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void testNotListDoctors() throws Exception {

		clearDoctors();

		this.mockMvc.perform(get("/anonymous/doctors")).andExpect(status().isOk())
				.andExpect(model().attributeExists("emptylist"))
				.andExpect(view().name("/anonymous/doctors/doctorsList"));
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
			fail(e.getMessage());
		}
	}
}
