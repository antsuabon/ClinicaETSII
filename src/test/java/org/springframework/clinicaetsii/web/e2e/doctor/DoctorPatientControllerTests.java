
package org.springframework.clinicaetsii.web.e2e.doctor;

import java.time.LocalDate;
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
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorPatientController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
class DoctorPatientControllerTests {

	private static final int TEST_PATIENT_ID_1 = 1;
	private static final int TEST_PATIENT_ID_2 = 2;

	@MockBean
	private PatientService patientService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Patient patient1;

	private Patient patient2;

	private Doctor doctor3;


	void setup() {

		this.doctor3 = new Doctor();
		this.doctor3.setId(3);
		this.doctor3.setUsername("doctor3");
		this.doctor3.setPassword("doctor3");
		this.doctor3.setEnabled(true);
		this.doctor3.setName("Antonio");
		this.doctor3.setSurname("Suarez Bono");
		this.doctor3.setDni("45612378P");
		this.doctor3.setEmail("antonio@gmail.com");
		this.doctor3.setPhone("955668756");
		this.doctor3.setCollegiateCode("303024345");

		this.patient1 = new Patient();
		this.patient1.setId(DoctorPatientControllerTests.TEST_PATIENT_ID_1);
		this.patient1.setAddress("Calle Oscar Arias");
		this.patient1.setBirthDate(LocalDate.now());
		this.patient1.setDni("41235678L");
		this.patient1.setEmail("pedro@gmail.com");
		this.patient1.setEnabled(true);
		this.patient1.setGeneralPractitioner(this.doctor3);
		this.patient1.setName("Pedro");
		this.patient1.setNss("12345778S");
		this.patient1.setPassword("patient1");
		this.patient1.setPhone("123456789");
		this.patient1.setState("España");
		this.patient1.setSurname("Roldán");
		this.patient1.setUsername("patient1");

		this.patient2 = new Patient();
		this.patient2.setId(DoctorPatientControllerTests.TEST_PATIENT_ID_2);
		this.patient2.setAddress("Calle Ramos Torres");
		this.patient2.setBirthDate(LocalDate.now());
		this.patient2.setDni("41235578L");
		this.patient2.setEmail("laura@gmail.com");
		this.patient2.setEnabled(true);
		this.patient2.setGeneralPractitioner(this.doctor3);
		this.patient2.setName("Laura");
		this.patient2.setNss("12345779S");
		this.patient2.setPassword("patient2");
		this.patient2.setPhone("123456789");
		this.patient2.setState("España");
		this.patient2.setSurname("Sánchez");
		this.patient2.setUsername("patient2");

		List<Patient> patients = new ArrayList<>();

		patients.add(this.patient1);
		patients.add(this.patient2);

		Collection<Patient> patients2 = patients;

		BDDMockito.given(this.patientService.findCurrentDoctorPatients()).willReturn(patients2);

	}

	@WithMockUser(value = "spring")
	@Test
	void testListPatients() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
				.andExpect(MockMvcResultMatchers.view().name("/doctor/patientsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testListPatientsWithOtherUserRole() throws Exception {
		BDDMockito.given(this.patientService.findCurrentDoctorPatients())
				.willThrow(AccessDeniedException.class);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListPatients() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/doctor/patientsList"));
	}

}
