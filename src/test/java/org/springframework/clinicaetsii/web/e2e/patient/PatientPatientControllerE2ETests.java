package org.springframework.clinicaetsii.web.e2e.patient;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PatientPatientControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldShowPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
				.andExpect(MockMvcResultMatchers.view().name("patient/patientDetails"));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotShowPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient")).andExpect(status().is(403));
	}

	@Test
	@WithMockUser(username = "administrative", authorities = {"administrative"})
	void administrativeShouldNotShowPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient")).andExpect(status().is(403));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotShowPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient")).andExpect(status().is(403));
	}



	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldInitUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientForm"))
				.andExpect(MockMvcResultMatchers.view().name("/patient/updatePatientForm"));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotInitUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(status().is(403));
	}

	@Test
	@WithMockUser(username = "administrative", authorities = {"administrative"})
	void administrativeShouldNotInitUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(status().is(403));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotInitUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(status().is(403));
	}


	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldNotProcessUpdatePatientForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/patient/edit")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("patient.name", "Pablo").param("patient.surname", "")
						.param("patient.dni", "").param("patient.email", "pablo@gmail.com")
						.param("patient.phone", "955668756").param("patient.username", "patient2")
						.param("newPassword", "").param("repeatPassword", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotProcessUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit").with(csrf())
				.param("patient.name", "Pablo").param("patient.surname", "Rodriguez Garrido")
				.param("patient.dni", "45612378P").param("patient.email", "pablo@gmail.com")
				.param("patient.phone", "955668756").param("patient.username", "patient2")
				.param("newPassword", "").param("repeatPassword", "")).andExpect(status().is(403));

	}


	@Test
	@WithMockUser(username = "administrative", authorities = {"administrative"})
	void administrativeShouldNotProcessUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("patient.name", "Pablo")
				.param("patient.surname", "Rodriguez Garrido").param("patient.dni", "45612378P")
				.param("patient.email", "pablo@gmail.com").param("patient.phone", "955668756")
				.param("patient.username", "patient2").param("newPassword", "")
				.param("repeatPassword", "")).andExpect(status().is(403));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotProcessUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("patient.name", "Pablo")
				.param("patient.surname", "Rodriguez Garrido").param("patient.dni", "45612378P")
				.param("patient.email", "pablo@gmail.com").param("patient.phone", "955668756")
				.param("patient.username", "patient2").param("newPassword", "")
				.param("repeatPassword", "")).andExpect(status().is(403));
	}


	@Test
	@WithMockUser(value = "spring")
	void shouldNotInitUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(status().is(403));
	}


	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void shouldProcessUpdatePatientForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("patient.name", "Pablo")
				.param("patient.surname", "Rodriguez Garrido")
				.param("patient.birthDate", "22/02/1982").param("patient.dni", "45612378P")
				.param("patient.address", "C/Calle de ejemplo").param("patient.state", "Sevilla")
				.param("patient.nss", "11111111111").param("patient.email", "pablo@gmail.com")
				.param("patient.phone", "955668756").param("patient.phone2", "955668756")
				.param("patient.username", "patient1").param("patient.generalPractitioner", "11"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/patient"));
	}


}
