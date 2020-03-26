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
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.formatter.DoctorFormatter;
import org.springframework.clinicaetsii.web.formatter.LocalDateFormatter;
import org.springframework.clinicaetsii.web.formatter.ServiceFormatter;
import org.springframework.clinicaetsii.web.patient.PatientAppointmentController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = PatientAppointmentController.class,
includeFilters= {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ServiceFormatter.class),
				 @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LocalDateFormatter.class),
				 @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DoctorFormatter.class),},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)

public class PatientAppointmentControllerTests {

	@Autowired
	private PatientAppointmentController patientAppointmentController;

	@MockBean
	private PatientService patientService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private ServiceFormatter		serviceFormatter;

	@MockBean
	private DoctorFormatter		doctorFormatter;

	@MockBean
	private DoctorService			doctorService;

	@Autowired
	private MockMvc	mockMvc;

	private Patient patient1;

	private Doctor doctor1;

	private Appointment	appointment1;

	private Appointment	appointment2;

	private DischargeType dischargeType1;

	private Consultation consultation1;

	private Collection<Appointment> appointmentsDone;

	private Collection<Appointment> appointmentsDelete;


	@BeforeEach
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
		this.appointment1.setId(PatientAppointmentControllerTests.TEST_APPOINTMENT_ID_1);
		this.appointment1.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 0));
		this.appointment1.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 0).plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.appointment2 = new Appointment();
		this.appointment2.setId(PatientAppointmentControllerTests.TEST_APPOINTMENT_ID_2);
		this.appointment2.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 21));
		this.appointment2.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 28));
		this.appointment2.setPatient(this.patient1);
		this.appointment2.setPriority(false);

		List<LocalDateTime> appointments = new ArrayList<>();

		appointments.add(this.appointment1.getStartTime());
		appointments.add(this.appointment2.getStartTime());

		Collection<LocalDateTime> appointments2 = appointments;

		BDDMockito.given(this.appointmentService.findAppointmentByDoctors(1)).willReturn(appointments2);
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findPatientByUsername()).willReturn(this.patient1);

		this.doctor1 = new Doctor();
		this.doctor1.setId(1);
		this.doctor1.setUsername("doctor");
		this.doctor1.setPassword("doctor");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Pablo");
		this.doctor1.setSurname("Rodriguez Garrido");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("pablo@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303092345");

		Service service1 = new Service();
		service1.setId(1);
		service1.setName("Consulta Niños");

		Service service2 = new Service();
		service2.setId(2);
		service2.setName("Vacunación de la Gripe");

		Collection<Service> services = new ArrayList<Service>();
		services.add(service1);

		this.doctor1.setServices(services);

		this.patient1 = new Patient();
		this.patient1.setId(2);
		this.patient1.setUsername("patient1");
		this.patient1.setPassword("patient1");
		this.patient1.setAddress("C/ Ejemplo");
		this.patient1.setBirthDate(LocalDate.of(1999, 11, 7));
		this.patient1.setDni("11111111A");
		this.patient1.setEmail("maria@gmail.com");
		this.patient1.setEnabled(true);
		this.patient1.setGeneralPractitioner(this.doctor1);
		this.patient1.setName("Maria");
		this.patient1.setSurname("Sanchez Noriega-Cruz");
		this.patient1.setNss("12345678900");
		this.patient1.setState("Sevilla");
		this.patient1.setPhone("666666666");
		this.patient1.setPhone2("999999999");

		this.appointment1 = new Appointment();
		this.appointment1.setStartTime(LocalDateTime.now());
		this.appointment1.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.consultation1 = new Consultation();
		this.consultation1.setId(1);
		this.consultation1.setStartTime(LocalDateTime.now());
		this.consultation1.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.consultation1.setAnamnesis("Dolor de estómago");
		this.consultation1.setRemarks("Fiebres altas");
		this.dischargeType1 = new DischargeType();
		this.dischargeType1.setId(1);
		this.dischargeType1.setName("Revisión");
		this.consultation1.setDischargeType(this.dischargeType1);
		this.consultation1.setAppointment(this.appointment1);

		this.appointment2 = new Appointment();
		this.appointment2.setStartTime(LocalDateTime.now().plusMinutes(7));
		this.appointment2.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.appointment2.setPatient(this.patient1);
		this.appointment2.setPriority(false);



		this.appointmentsDone = new HashSet<>();
		this.appointmentsDone.add(this.appointment1);
		this.appointmentsDelete = new HashSet<>();
		this.appointmentsDelete.add(this.appointment2);
	}

	@WithMockUser(value = "spring")
	@Test
	void testGenerateTable() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/table")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hours"))
			.andExpect(MockMvcResultMatchers.view().name("/patient/appointments/timeTable"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitForm() throws Exception {
		this.setup();
		String fecha = LocalDateTime.now().plusHours(8).format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/new").param("fecha", fecha)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
			.andExpect(MockMvcResultMatchers.view().name("/patient/appointments/requestAppointment"));
	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void testProcessCreationForm() throws Exception {
		this.setup();

		String startTime = LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);
		String endTime =  LocalDateTime.of(2020, 3, 26, 9, 0).plusMinutes(7).format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/appointments/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", startTime).param("endTime", endTime)).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/patient/appointments/table"));

	}

	@Test
	@WithMockUser(username = "patient1", roles = {
		"patient"
	})
	void testNotProcessCreationForm() throws Exception {
		this.setup();

		String startTime = LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/appointments/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("startTime", startTime)).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/patient/appointments/new"));

	}


	@Test
	@WithMockUser(username = "patient", roles = {"patient"})
	void shouldListAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findAppointmentsDelete()).willReturn(this.appointmentsDelete);
		BDDMockito.given(this.patientService.findAppointmentsDone()).willReturn(this.appointmentsDone);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
					.andExpect(MockMvcResultMatchers.model().attributeExists("appointmentsDone"))
					.andExpect(MockMvcResultMatchers.model().attributeExists("appointmentsDelete"))
					.andExpect(MockMvcResultMatchers.view().name("/patient/appointments/appointmentsList"));

	}

	@Test
	@WithMockUser(username = "doctor", roles = {"doctor"})
	void doctorShouldNotListAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.patientService.findAppointmentsDelete()).willThrow(new RuntimeException());
		BDDMockito.given(this.patientService.findAppointmentsDone()).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
					.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("appointmentsDone"))
					.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("appointmentsDelete"))
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void administrativeShouldNotListAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.patientService.findAppointmentsDelete()).willThrow(new RuntimeException());
		BDDMockito.given(this.patientService.findAppointmentsDone()).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
					.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("appointmentsDone"))
					.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("appointmentsDelete"))
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}



	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotListAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.patientService.findAppointmentsDelete()).willThrow(new RuntimeException());
		BDDMockito.given(this.patientService.findAppointmentsDone()).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
					.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("appointmentsDone"))
					.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("appointmentsDelete"))
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}



	@Test
	@WithMockUser(username = "patient", roles = {"patient"})
	void shouldDeleteAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.appointmentService.findAppointmentById(2)).willReturn(this.appointment2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",2))
					.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
					.andExpect(MockMvcResultMatchers.view().name("redirect:/patient/appointments"));

	}


	@Test
	@WithMockUser(username = "patient", roles = {"patient"})
	void shouldNotDeleteAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.appointmentService.findAppointmentById(-1)).willThrow(new RuntimeException());


		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",-1))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "patient", roles = {"patient"})
	void shouldNotDeleteAppointmentsDone() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.appointmentService.findAppointmentById(1)).willReturn(this.appointment1);
		BDDMockito.given(this.patientService.findAppointmentsDone()).willReturn(this.appointmentsDone);


		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",1))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "doctor", roles = {"doctor"})
	void doctorShouldNotDeleteAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.appointmentService.findAppointmentById(2)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",2))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void administrativeShouldNotDeleteAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.appointmentService.findAppointmentById(2)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",2))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}



	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotDeleteAppointments() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.appointmentService.findAppointmentById(2)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",2))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "doctor", roles = {"doctor"})
	void doctorShouldNotDeleteAppointmentsDone() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.appointmentService.findAppointmentById(1)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",1))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void administrativeShouldNotDeleteAppointmentsDone() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.appointmentService.findAppointmentById(1)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",1))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}



	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotDeleteAppointmentsDone() throws Exception {

		BDDMockito.given(this.patientService.findCurrentPatient()).willThrow(new RuntimeException());
		BDDMockito.given(this.appointmentService.findAppointmentById(1)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",1))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


}
