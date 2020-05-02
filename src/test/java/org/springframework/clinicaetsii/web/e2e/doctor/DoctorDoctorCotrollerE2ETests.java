
package org.springframework.clinicaetsii.web.e2e.doctor;

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
public class DoctorDoctorCotrollerE2ETests {



	@Autowired
	private MockMvc mockMvc;



	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldShowDoctor() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("doctor/doctorDetails"));
	}

	@Test
	@WithMockUser(value = "spring")
	void shouldNotShowDoctor() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldInitUpdateDoctorForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctorForm"))
				.andExpect(MockMvcResultMatchers.view().name("/doctor/updateDoctorForm"));
	}

	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void patientShouldNotInitUpdateDoctorForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/edit"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotInitUpdateDoctorForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/edit"))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldProcessUpdateDoctorFormWithoutNewPassword() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido").param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com").param("doctor.phone", "955668756")
				.param("doctor.username", "doctor1").param("newPassword", "")
				.param("repeatPassword", "").param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldProcessUpdateDoctorFormWithNullNewPassword() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido").param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com").param("doctor.phone", "955668756")
				.param("doctor.username", "doctor1").param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldProcessUpdateDoctorFormWithPassword() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido").param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com").param("doctor.phone", "955668756")
				.param("doctor.username", "doctor1").param("newPassword", "aaaaaaA1@")
				.param("repeatPassword", "aaaaaaA1@").param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void shouldProcessUpdateDoctorFormUsername() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido").param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com").param("doctor.phone", "955668756")
				.param("doctor.username", "doctor122").param("newPassword", "")
				.param("repeatPassword", "").param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/logout"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldNotProcessUpdateDoctorFormUsername() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/doctor/edit")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("doctor.name", "Pablo").param("doctor.surname", "Rodriguez Garrido")
						.param("doctor.dni", "45612378P").param("doctor.email", "pablo@gmail.com")
						.param("doctor.phone", "955668756").param("doctor.username", "doctor2")
						.param("newPassword", "").param("repeatPassword", "")
						.param("doctor.collegiateCode", "303092345").param("doctor.services", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/doctor/updateDoctorForm"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void shouldNotProcessUpdateDoctorForm() throws Exception {



		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/doctor/edit")
						.with(SecurityMockMvcRequestPostProcessors.csrf()).param("doctor.name", "")
						.param("doctor.surname", "").param("doctor.dni", "")
						.param("doctor.email", "").param("doctor.phone", "")
						.param("doctor.username", "doctor1").param("newPassword", "")
						.param("repeatPassword", "").param("doctor.collegiateCode", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("doctorForm"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm",
						"doctor.name"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm",
						"doctor.surname"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm",
						"doctor.dni"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm",
						"doctor.email"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm",
						"doctor.phone"))
				.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm",
						"doctor.dni"))
				.andExpect(MockMvcResultMatchers.view().name("/doctor/updateDoctorForm"));
	}

}
