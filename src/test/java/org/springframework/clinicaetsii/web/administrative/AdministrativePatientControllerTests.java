
package org.springframework.clinicaetsii.web.administrative;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.web.formatter.DoctorFormatter;
import org.springframework.clinicaetsii.web.formatter.LocalDateFormatter;
import org.springframework.clinicaetsii.web.formatter.ServiceFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = AdministrativePatientController.class, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ServiceFormatter.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = LocalDateFormatter.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DoctorFormatter.class),},
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class AdministrativePatientControllerTests {

	@Autowired
	private AdministrativePatientController administrativePatientController;

	@MockBean
	private PatientService patientService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private DoctorService doctorService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	private Administrative administrative;

	private Patient patient1;

	private Doctor doctor1;

	private Collection<Service> services;

	private Appointment appointment1;
	private Appointment appointment2;

	private static int TEST_APPOINTMENT_ID_1 = 1;
	private static int TEST_APPOINTMENT_ID_2 = 2;

	private static int TEST_PATIENT_ID = 1;

	@BeforeEach
	void setup() {


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
		this.patient1.setId(TEST_PATIENT_ID);
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
		this.appointment1.setId(AdministrativePatientControllerTests.TEST_APPOINTMENT_ID_1);
		this.appointment1.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 0));
		this.appointment1.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 0).plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.appointment2 = new Appointment();
		this.appointment2.setId(AdministrativePatientControllerTests.TEST_APPOINTMENT_ID_2);
		this.appointment2.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 21));
		this.appointment2.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 28));
		this.appointment2.setPatient(this.patient1);
		this.appointment2.setPriority(false);

		List<LocalDateTime> appointments = new ArrayList<>();
		appointments.add(this.appointment1.getStartTime());
		appointments.add(this.appointment2.getStartTime());

		List<Patient> patients = new ArrayList<>();
		patients.add(this.patient1);

		BDDMockito.given(this.patientService.findPatients()).willReturn(patients);
		BDDMockito.given(this.appointmentService.findAppointmentByDoctors(1))
				.willReturn(appointments);
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findPatientById(1)).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findDoctorByPatient(1)).willReturn(this.doctor1);
		BDDMockito.given(this.patientService.findDoctorByPatient(-1)).willReturn(null);


		this.administrative = new Administrative();
		this.administrative.setId(1);
		this.administrative.setUsername("administrative");
		this.administrative.setPassword("administrative");
		this.administrative.setDni("11111111A");
		this.administrative.setEmail("maria@gmail.com");
		this.administrative.setEnabled(true);
		this.administrative.setName("Maria");
		this.administrative.setSurname("Sanchez Noriega-Cruz");
		this.administrative.setPhone("666666666");


		this.doctor1 = new Doctor();
		this.doctor1.setId(2);
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
		service1.setId(3);
		service1.setName("Consulta Niños");

		Service service2 = new Service();
		service2.setId(4);
		service2.setName("Vacunación de la Gripe");

		this.services = new ArrayList<Service>();
		this.services.add(service1);

		this.doctor1.setServices(this.services);

	}

	@WithMockUser(username = "administrative1", roles = {"administrative"})
	@Test
	void shouldListPatients() throws Exception {
		this.mockMvc.perform(get("/administrative/patients")).andExpect(status().isOk())
				.andExpect(model().attributeExists("patients"))
				.andExpect(view().name("/administrative/patients/patientsList"));
	}

	@WithMockUser(username = "administrative1", roles = {"administrative"})
	@Test
	void shouldNotListPatients() throws Exception {
		given(this.patientService.findPatients()).willReturn(new ArrayList<>());
		this.mockMvc.perform(get("/administrative/patients")).andExpect(status().isOk())
				.andExpect(model().attributeExists("emptyList"))
				.andExpect(view().name("/administrative/patients/patientsList"));
	}

	@WithMockUser(username = "administrative1", roles = {"administrative"})
	@Test
	void shouldShowPatient() throws Exception {
		this.mockMvc.perform(get("/administrative/patients/{patientId}", TEST_PATIENT_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("patient"))
				.andExpect(view().name("/administrative/patients/patientDetails"));
	}

	@WithMockUser(username = "administrative1", roles = {"administrative"})
	@Test
	void shouldNotShowPatient() throws Exception {
		this.mockMvc.perform(get("/administrative/patients/{patientId}", -1))
				.andExpect(status().isOk()).andExpect(model().attributeDoesNotExist("patient"))
				.andExpect(view().name("exception"));
	}

	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void shouldInitCreatePatientForm() throws Exception {

		given(this.patientService.findCurrentAdministrative()).willReturn(this.administrative);

		this.mockMvc.perform(get("/administrative/patients/new"))
				.andExpect(model().attributeExists("patient"))
				.andExpect(view().name("/administrative/patients/createPatientForm"));

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void doctorShouldNotInitCreatePatientForm() throws Exception {

		given(this.patientService.findCurrentAdministrative()).willThrow(new RuntimeException());

		this.mockMvc.perform(get("/administrative/patients/new")).andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("patient"))
				.andExpect(view().name("exception"));

	}

	@Test
	@WithMockUser(username = "patient", roles = {"patient"})
	void patientShouldNotInitCreatePatientForm() throws Exception {

		given(this.patientService.findCurrentAdministrative()).willThrow(new RuntimeException());

		this.mockMvc.perform(get("/administrative/patients/new")).andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("patient"))
				.andExpect(view().name("exception"));

	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotInitCreatePatientForm() throws Exception {

		given(this.patientService.findCurrentAdministrative()).willThrow(new RuntimeException());

		this.mockMvc.perform(get("/administrative/patients/new")).andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("patient"))
				.andExpect(view().name("exception"));

	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void shouldProcessCreatePatientForm() throws Exception {

		given(this.patientService.findCurrentAdministrative()).willReturn(this.administrative);

		given(this.doctorService.findDoctorById(2)).willReturn(this.doctor1);

		// Mockito.doAnswer(invocation->{
		// Patient patient = (Patient) invocation.getArgument(0);
		// patient.setId(5);
		// return null;
		// }).when(this.patientService).savePatient(this.patient1);

		this.mockMvc
				.perform(post("/administrative/patients/new").with(csrf())
						.param("username", "pablo").param("name", "Pablo")
						.param("surname", "Rodriguez Garrido").param("address", "C/ Ejemplo")
						.param("birthDate", "02/02/2020").param("dni", "45612378P")
						.param("email", "pablo@gmail.com").param("nss", "12345678900")
						.param("state", "Sevilla").param("phone", "955668756").param("phone2", "")
						.param("generalPractitioner", "2"))
				.andDo(print()).andExpect(status().is3xxRedirection());
	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void shouldNotProcessCreatePatientForm() throws Exception {
		given(this.patientService.findCurrentAdministrative()).willReturn(this.administrative);

		this.mockMvc
				.perform(post("/administrative/patients/new").with(csrf())
						.param("username", "pablo").param("name", "").param("surname", "")
						.param("address", "").param("birthDate", "").param("dni", "")
						.param("email", "").param("nss", "").param("state", "").param("phone", "")
						.param("phone2", "").param("generalPractitioner", ""))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("patient"))
				.andExpect(model().attributeHasFieldErrors("patient", "name"))
				.andExpect(model().attributeHasFieldErrors("patient", "surname"))
				.andExpect(model().attributeHasFieldErrors("patient", "dni"))
				.andExpect(model().attributeHasFieldErrors("patient", "email"))
				.andExpect(model().attributeHasFieldErrors("patient", "phone"))
				.andExpect(model().attributeHasFieldErrors("patient", "dni"))
				.andExpect(model().attributeHasFieldErrors("patient", "birthDate"))
				.andExpect(model().attributeHasFieldErrors("patient", "nss"))
				.andExpect(model().attributeHasFieldErrors("patient", "address"))
				.andExpect(model().attributeHasFieldErrors("patient", "state"))
				.andExpect(model().attributeHasFieldErrors("patient", "generalPractitioner"))
				.andExpect(view().name("/administrative/patients/createPatientForm"));
	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void shouldProcessCreatePatientFormPhone2() throws Exception {

		given(this.patientService.findCurrentAdministrative()).willReturn(this.administrative);

		given(this.doctorService.findDoctorById(2)).willReturn(this.doctor1);

		this.mockMvc
				.perform(post("/administrative/patients/new").with(csrf())
						.param("username", "pablo").param("name", "Pablo")
						.param("surname", "Rodriguez Garrido").param("address", "C/ Ejemplo")
						.param("birthDate", "02/02/2020").param("dni", "45612378P")
						.param("email", "pablo@gmail.com").param("nss", "12345678900")
						.param("state", "Sevilla").param("phone", "955668756")
						.param("phone2", "955865502").param("generalPractitioner", "2"))
				.andDo(print()).andExpect(status().is3xxRedirection());
	}

}
