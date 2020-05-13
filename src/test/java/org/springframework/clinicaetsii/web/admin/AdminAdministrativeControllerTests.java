package org.springframework.clinicaetsii.web.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Administrative;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.AdministrativeService;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.service.exceptions.DeleteDoctorException;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminAdministrativeController.class,
includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = LocalDateTimeFormatter.class),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminAdministrativeControllerTests {
	@MockBean
	private AdministrativeService administrativeService;

	@Autowired
	private AdminAdministrativeController administrativeDoctorController;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Administrative administrative1;
	private Administrative administrative2;
	private Administrative administrative3;

	private List<Administrative> administratives;


	private static final int TEST_ADMINISTRATIVE_ID_1 = 1;
	private static final int TEST_ADMINISTRATIVE_ID_2 = 2;
	private static final int TEST_ADMINISTRATIVE_ID_3 = 3;


	@BeforeEach
	void setup() {

		this.administrative1 = new Administrative();
		this.administrative1.setId(1);
		this.administrative1.setUsername("doctor1");
		this.administrative1.setPassword("doctor1");
		this.administrative1.setEnabled(true);
		this.administrative1.setName("Antonio");
		this.administrative1.setSurname("Suarez Bono");
		this.administrative1.setDni("45612378P");
		this.administrative1.setEmail("antonio@gmail.com");
		this.administrative1.setPhone("955668756");

		this.administrative2 = new Administrative();
		this.administrative2.setId(3);
		this.administrative2.setUsername("doctor3");
		this.administrative2.setPassword("doctor3");
		this.administrative2.setEnabled(true);
		this.administrative2.setName("Antonio");
		this.administrative2.setSurname("Suarez Bono");
		this.administrative2.setDni("45612378P");
		this.administrative2.setEmail("antonio@gmail.com");
		this.administrative2.setPhone("955668756");


		this.administratives = new ArrayList<>();
		this.administratives.add(this.administrative1);
		this.administratives.add(this.administrative2);


		BDDMockito.given(this.administrativeService.findAllAdministratives()).willReturn(this.administratives);
		BDDMockito
				.given(this.administrativeService
						.findAdministrativeById(AdminAdministrativeControllerTests.TEST_ADMINISTRATIVE_ID_1))
				.willReturn(this.administrative1);
		BDDMockito
				.given(this.administrativeService
						.findAdministrativeById(AdminAdministrativeControllerTests.TEST_ADMINISTRATIVE_ID_2))
				.willReturn(this.administrative2);
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void testListAdministratives() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/administratives"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("administratives"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/administratives/administrativesList"));
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void testNotListAdministratives() throws Exception {
		List<Administrative> administratives = new ArrayList<>();
		BDDMockito.given(this.administrativeService.findAllAdministratives()).willReturn(administratives);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/administratives"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/administratives/administrativesList"));
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void testFindAdministrativeById() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/administratives/{administrativeId}",
						AdminAdministrativeControllerTests.TEST_ADMINISTRATIVE_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("administrative"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/administratives/administrativeDetails"));
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void shouldNotShowAdministrative() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/administratives/{administrativeId}", -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("administrative"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", roles = "admin")
	@Test
	void shouldDeleteAdministrative() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/administratives/{administrativeId}/delete",
						TEST_ADMINISTRATIVE_ID_1))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/administratives"));
	}





}
