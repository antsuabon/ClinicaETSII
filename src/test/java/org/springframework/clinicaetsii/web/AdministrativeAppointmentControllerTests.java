
package org.springframework.clinicaetsii.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.administrative.AdministrativeAppointmentController;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = AdministrativeAppointmentController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LocalDateTimeFormatter.class),
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class AdministrativeAppointmentControllerTests {

	private static final int					TEST_APPOINTMENT_ID_1	= 1;
	private static final int					TEST_APPOINTMENT_ID_2	= 2;

	@MockBean
	private PatientService						patientService;

	@Autowired
	private WebApplicationContext				webApplicationContext;

	@Autowired
	private AdministrativeAppointmentController	administrativeAppointmentController;

	@MockBean
	private AuthoritiesService					authoritiesService;

	@MockBean
	private AppointmentService					appointmentService;

	@Autowired
	private MockMvc								mockMvc;

	private Doctor								doctor1;

	private Patient								patient1;

	private Appointment							appointment1;

	private Appointment							appointment2;


	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		this.doctor1 = new Doctor();
		this.doctor1.setId(1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Antonio");
		this.doctor1.setSurname("Suarez Bono");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("antonio@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303024345");

		this.patient1 = new Patient();
		this.patient1.setId(1);
		this.patient1.setAddress("Calle Oscar Arias");
		this.patient1.setBirthDate(LocalDate.now());
		this.patient1.setDni("41235678L");
		this.patient1.setEmail("pedro@gmail.com");
		this.patient1.setEnabled(true);
		this.patient1.setGeneralPractitioner(this.doctor1);
		this.patient1.setName("Pedro");
		this.patient1.setNss("12345778S");
		this.patient1.setPassword("patient1");
		this.patient1.setPhone("123456789");
		this.patient1.setState("España");
		this.patient1.setSurname("Roldán");
		this.patient1.setUsername("patient1");

		this.appointment1 = new Appointment();
		this.appointment1.setId(AdministrativeAppointmentControllerTests.TEST_APPOINTMENT_ID_1);
		this.appointment1.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 0));
		this.appointment1.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 0).plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.appointment2 = new Appointment();
		this.appointment2.setId(AdministrativeAppointmentControllerTests.TEST_APPOINTMENT_ID_2);
		this.appointment2.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 21));
		this.appointment2.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 28));
		this.appointment2.setPatient(this.patient1);
		this.appointment2.setPriority(false);

		List<LocalDateTime> appointments = new ArrayList<>();
		List<Patient> patients = new ArrayList<>();

		appointments.add(this.appointment1.getStartTime());
		appointments.add(this.appointment2.getStartTime());
		patients.add(this.patient1);

		Collection<LocalDateTime> appointments2 = appointments;
		Collection<Patient> patients2 = patients;

		BDDMockito.given(this.patientService.findPatients()).willReturn(patients2);
		BDDMockito.given(this.appointmentService.findAppointmentByDoctors(1)).willReturn(appointments2);
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findPatientByUsername()).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findPatient(1)).willReturn(this.patient1);

	}

	@WithMockUser(username = "administrative1", roles = {
		"administrative"
	})
	@Test
	void testGenerateTable() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/{patientId}/appointments/table", 1)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hours"))
			.andExpect(MockMvcResultMatchers.view().name("/administrative/timeTable"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitForm() throws Exception {
		this.setup();
		String fecha = LocalDateTime.now().plusHours(8).format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/patients/{patientId}/appointments/new", 1).param("fecha", fecha)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("appointment")).andExpect(MockMvcResultMatchers.model().attributeExists("patientId")).andExpect(MockMvcResultMatchers.view().name("/administrative/requestAppointment"));
	}

	@Test
	@WithMockUser(username = "administrative1", roles = {
		"administrative"
	})
	void testProcessCreationForm() throws Exception {
		this.setup();

		String startTime = LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);
		String endTime = LocalDateTime.of(2020, 3, 26, 9, 0).plusMinutes(7).format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/administrative/patients/{patientId}/appointments/save", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", startTime).param("endTime", endTime))
			.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/administrative/patients/{patientId}/appointments/table"));

	}

	@Test
	@WithMockUser(username = "administrative1", roles = {
		"administrative"
	})
	void testProcessNotCreationForm() throws Exception {
		this.setup();

		String startTime = LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/administrative/patients/{patientId}/appointments/save", 1).with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", startTime)).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/administrative/patients/{patientId}/appointments/new"));

	}

}
