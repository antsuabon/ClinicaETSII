
package org.springframework.clinicaetsii.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class DoctorControllerTests {

	private static final int	TEST_DOCTOR_ID_1	= 1;
	private static final int	TEST_DOCTOR_ID_2	= 2;
	private static final int	TEST_DOCTOR_ID_3	= 3;

	@MockBean
	private DoctorService		doctorService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Doctor				doctor1;

	private Doctor				doctor2;

	private Doctor				doctor3;


	void setup() {

		this.doctor1 = new Doctor();
		this.doctor1.setId(DoctorControllerTests.TEST_DOCTOR_ID_1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Pablo");
		this.doctor1.setSurname("Rodriguez Garrido");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("pablo@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303092345");

		this.doctor2 = new Doctor();
		this.doctor2.setId(DoctorControllerTests.TEST_DOCTOR_ID_2);
		this.doctor2.setUsername("doctor2");
		this.doctor2.setPassword("doctor2");
		this.doctor2.setEnabled(true);
		this.doctor2.setName("Alejandro");
		this.doctor2.setSurname("Sánchez López");
		this.doctor2.setDni("45612555S");
		this.doctor2.setEmail("ale@gmail.com");
		this.doctor2.setPhone("955668777");
		this.doctor2.setCollegiateCode("303051345");

		this.doctor3 = new Doctor();
		this.doctor3.setId(DoctorControllerTests.TEST_DOCTOR_ID_3);
		this.doctor3.setUsername("doctor3");
		this.doctor3.setPassword("doctor3");
		this.doctor3.setEnabled(true);
		this.doctor3.setName("Antonio");
		this.doctor3.setSurname("Suarez Bono");
		this.doctor3.setDni("45612378P");
		this.doctor3.setEmail("antonio@gmail.com");
		this.doctor3.setPhone("955668756");
		this.doctor3.setCollegiateCode("303024345");

		List<Doctor> doctors = new ArrayList<>();

		doctors.add(this.doctor1);
		doctors.add(this.doctor2);
		doctors.add(this.doctor3);

		Collection<Doctor> doctors2 = doctors;

		BDDMockito.given(this.doctorService.findDoctorsSortedByNumOfServices())
			.willReturn(doctors2);

	}

	@WithMockUser(value = "spring")
	@Test
	void testListDoctors() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anonymous/doctors"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("doctors"))
			.andExpect(MockMvcResultMatchers.view().name("/anonymous/doctors/doctorsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListDoctors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/anonymous/doctors"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("emptylist"))
			.andExpect(MockMvcResultMatchers.view().name("/anonymous/doctors/doctorsList"));
	}

}
