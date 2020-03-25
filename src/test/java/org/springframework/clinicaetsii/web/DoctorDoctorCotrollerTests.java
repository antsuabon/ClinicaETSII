
package org.springframework.clinicaetsii.web;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.model.form.DoctorForm;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.web.doctor.DoctorDoctorController;
import org.springframework.clinicaetsii.web.formatter.ServiceFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorDoctorController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ServiceFormatter.class), excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class DoctorDoctorCotrollerTests {

	@Autowired
	private DoctorDoctorController	doctorDoctorController;

	@MockBean
	private DoctorService			doctorService;

	@Autowired
	private MockMvc					mockMvc;

	private int						TEST_DOCTOR_ID_1	= 1;
	private Doctor					doctor1;
	private int						TEST_DOCTOR_ID_2	= 2;
	private Doctor					doctor2;
	private DoctorForm				doctorForm1;
	private Collection<Service> services;

	private Service service1;
	private Service service2;

	@BeforeEach
	void initDoctors() {
		this.doctor1 = new Doctor();
		this.doctor1.setId(this.TEST_DOCTOR_ID_1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Pablo");
		this.doctor1.setSurname("Rodriguez Garrido");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("pablo@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303092345");

		this.service1 = new Service();
		this.service1.setId(1);
		this.service1.setName("Consulta Niños");

		this.service2 = new Service();
		this.service2.setId(4);
		this.service2.setName("Vacunación de la Gripe");

		this.services = new ArrayList<Service>();
		this.services.add(this.service1);
		this.services.add(this.service2);

		this.doctorForm1 = new DoctorForm();

		this.doctor1.setServices(this.services);


		this.doctor2 = new Doctor();
		this.doctor2.setId(this.TEST_DOCTOR_ID_2);
		this.doctor2.setUsername("doctor2");

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldShowDoctor() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("doctor"))
			.andExpect(MockMvcResultMatchers.view().name("doctor/doctorDetails"));
	}

	@Test
	@WithMockUser(value = "spring")
	void shouldNotShowDoctor() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("doctor"))
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldInitUpdateDoctorForm() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/edit"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("doctorForm"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/updateDoctorForm"));
	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void patientShouldNotInitUpdateDoctorForm() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/edit"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotInitUpdateDoctorForm() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/edit"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldProcessUpdateDoctorFormWithoutNewPassword() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		BDDMockito.given(this.doctorService.findAllServices()).willReturn(this.services);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido")
				.param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com")
				.param("doctor.phone", "955668756")
				.param("doctor.username", "doctor1")
				.param("newPassword", "")
				.param("repeatPassword", "")
				.param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldProcessUpdateDoctorFormWithNullNewPassword() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		BDDMockito.given(this.doctorService.findAllServices()).willReturn(this.services);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido")
				.param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com")
				.param("doctor.phone", "955668756")
				.param("doctor.username", "doctor1")
				.param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldProcessUpdateDoctorFormWithPassword() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		BDDMockito.given(this.doctorService.findAllServices()).willReturn(this.services);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido")
				.param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com")
				.param("doctor.phone", "955668756")
				.param("doctor.username", "doctor1")
				.param("newPassword", "aaaaaaA1@")
				.param("repeatPassword", "aaaaaaA1@")
				.param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldProcessUpdateDoctorFormUsername() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		BDDMockito.given(this.doctorService.findAllServices()).willReturn(this.services);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido")
				.param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com")
				.param("doctor.phone", "955668756")
				.param("doctor.username", "doctor122")
				.param("newPassword", "")
				.param("repeatPassword", "")
				.param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1", "4"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/logout"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldNotProcessUpdateDoctorFormUsername() throws Exception {
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		BDDMockito.given(this.doctorService.findDoctorByUsername("doctor2")).willReturn(this.doctor2);
		BDDMockito.given(this.doctorService.findAllServices()).willReturn(this.services);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("doctor.name", "Pablo")
				.param("doctor.surname", "Rodriguez Garrido")
				.param("doctor.dni", "45612378P")
				.param("doctor.email", "pablo@gmail.com")
				.param("doctor.phone", "955668756")
				.param("doctor.username", "doctor2")
				.param("newPassword", "")
				.param("repeatPassword", "")
				.param("doctor.collegiateCode", "303092345")
				.param("doctor.services", "1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("/doctor/updateDoctorForm"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldNotProcessUpdateDoctorForm() throws Exception {

		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);

		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/doctor/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("doctor.name", "")
				.param("doctor.surname", "")
				.param("doctor.dni", "")
				.param("doctor.email", "")
				.param("doctor.phone", "")
				.param("doctor.username", "doctor1")
				.param("newPassword", "")
				.param("repeatPassword", "")
				.param("doctor.collegiateCode", ""))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("doctorForm"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm", "doctor.name"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm", "doctor.surname"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm", "doctor.dni"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm", "doctor.email"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm", "doctor.phone"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("doctorForm", "doctor.dni"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/updateDoctorForm"));
	}

}
