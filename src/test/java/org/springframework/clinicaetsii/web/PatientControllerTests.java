 package org.springframework.clinicaetsii.web;


import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.web.patient.PatientPatientController;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import org.mockito.BDDMockito;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Authorities;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

@WebMvcTest(controllers = PatientPatientController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PatientControllerTests {
	private static final int	TEST_DOCTOR_ID_1	= 1;
	private static final int	TEST_DOCTOR_ID_2	= 2;
	private static final int	TEST_DOCTOR_ID_3	= 3;

	@Autowired
	private DoctorController	doctorController;

	@MockBean
	private DoctorService		doctorService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Doctor				doctor1;

	private Doctor				doctor2;

	private Doctor				doctor3;

	private Authorities			authority1;

	private Authorities			authority2;

	private Authorities			authority3;


	@BeforeEach
	void setup() {

		this.doctor1 = new Doctor();
		this.doctor1.setId(PatientControllerTests.TEST_DOCTOR_ID_1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Pablo");
		this.doctor1.setSurname("Rodriguez Garrido");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("pablo@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303092345");

		this.authority1.setAuthority("doctor");

		this.doctor2 = new Doctor();
		this.doctor2.setId(PatientControllerTests.TEST_DOCTOR_ID_2);
		this.doctor2.setUsername("doctor2");
		this.doctor2.setPassword("doctor2");
		this.doctor2.setEnabled(true);
		this.doctor2.setName("Alejandro");
		this.doctor2.setSurname("Sánchez López");
		this.doctor2.setDni("45612555S");
		this.doctor2.setEmail("ale@gmail.com");
		this.doctor2.setPhone("955668777");
		this.doctor2.setCollegiateCode("303051345");

		this.authority2.setAuthority("doctor");

		this.doctor3 = new Doctor();
		this.doctor3.setId(PatientControllerTests.TEST_DOCTOR_ID_3);
		this.doctor3.setUsername("doctor3");
		this.doctor3.setPassword("doctor3");
		this.doctor3.setEnabled(true);
		this.doctor3.setName("Antonio");
		this.doctor3.setSurname("Suarez Bono");
		this.doctor3.setDni("45612378P");
		this.doctor3.setEmail("antonio@gmail.com");
		this.doctor3.setPhone("955668756");
		this.doctor3.setCollegiateCode("303024345");

		this.authority3.setAuthority("doctor");

		List<Doctor> doctors = new ArrayList<>();

		doctors.add(this.doctor1);
		doctors.add(this.doctor2);
		doctors.add(this.doctor3);

		Collection<Doctor> doctors2 = doctors;

		BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(doctors2);

	}

}
