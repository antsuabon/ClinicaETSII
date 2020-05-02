
package org.springframework.clinicaetsii.web.e2e.administrative;

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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AdministrativePatientControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	private static int TEST_APPOINTMENT_ID_1 = 1;
	private static int TEST_APPOINTMENT_ID_2 = 2;

	private static int TEST_PATIENT_ID = 4;

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void shouldListPatients() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patients")).andExpect(
						MockMvcResultMatchers.view().name("/administrative/patients/patientsList"));
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void shouldNotListPatients() throws Exception {

		clearPatients();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList")).andExpect(
						MockMvcResultMatchers.view().name("/administrative/patients/patientsList"));
	}

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void shouldShowPatient() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/administrative/patients/{patientId}",
						AdministrativePatientControllerE2ETests.TEST_PATIENT_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/patients/patientDetails"));
	}

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void shouldNotShowPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/{patientId}", -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patient"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void shouldInitCreatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/new"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/patients/createPatientForm"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotInitCreatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/new"))
				.andExpect(MockMvcResultMatchers.status().is(403));

	}

	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void patientShouldNotInitCreatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/new"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotInitCreatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/new"))
				.andExpect(MockMvcResultMatchers.status().is(403));

	}


	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void shouldProcessCreatePatientForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/administrative/patients/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("username", "pablo").param("name", "Pablo")
						.param("surname", "Rodriguez Garrido").param("address", "C/ Ejemplo")
						.param("birthDate", "02/02/2020").param("dni", "45612378P")
						.param("email", "pablo@gmail.com").param("nss", "12345678900")
						.param("state", "Sevilla").param("phone", "955668756").param("phone2", "")
						.param("generalPractitioner", "2"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}


	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void shouldNotProcessCreatePatientForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/administrative/patients/new")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("username", "pablo").param("name", "").param("surname", "")
						.param("address", "").param("birthDate", "").param("dni", "")
						.param("email", "").param("nss", "").param("state", "").param("phone", "")
						.param("phone2", "").param("generalPractitioner", ""))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("patient"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "name"))
				.andExpect(
						MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "surname"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "dni"))
				.andExpect(
						MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "email"))
				.andExpect(
						MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "phone"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "dni"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient",
						"birthDate"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "nss"))
				.andExpect(
						MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "address"))
				.andExpect(
						MockMvcResultMatchers.model().attributeHasFieldErrors("patient", "state"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("patient",
						"generalPractitioner"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/administrative/patients/createPatientForm"));
	}

	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void shouldProcessCreatePatientFormPhone2() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/administrative/patients/new")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("username", "pablo")
				.param("name", "Pablo").param("surname", "Rodriguez Garrido")
				.param("address", "C/ Ejemplo").param("birthDate", "02/02/2020")
				.param("dni", "45612378P").param("email", "pablo@gmail.com")
				.param("nss", "12345678900").param("state", "Sevilla").param("phone", "955668756")
				.param("phone2", "955865502").param("generalPractitioner", "2"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
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
