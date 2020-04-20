package org.springframework.clinicaetsii.web.e2e.admin;

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
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.model.User;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.service.exceptions.DeletePatientException;
import org.springframework.clinicaetsii.web.formatter.DoctorFormatter;
import org.springframework.clinicaetsii.web.formatter.LocalDateFormatter;
import org.springframework.clinicaetsii.web.formatter.ServiceFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminPatientController.class, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ServiceFormatter.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = LocalDateFormatter.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DoctorFormatter.class),},
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminPatientControllerTests {


	@Autowired
	private AdminPatientController adminPatientController;

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

	private User admin;

	private Patient patient1;

	private Patient patient2;

	private Doctor doctor1;

	private Collection<Service> services;

	private Appointment appointment1;
	private Appointment appointment2;

	private Patient patient9;
	private Patient patient5;

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
		this.patient1.setId(2);
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

		this.patient2 = new Patient();
		this.patient2.setId(3);
		this.patient2.setAddress("Calle Oscar Arias");
		this.patient2.setBirthDate(LocalDate.now());
		this.patient2.setDni("41235678L");
		this.patient2.setEmail("pedro@gmail.com");
		this.patient2.setEnabled(true);
		this.patient2.setGeneralPractitioner(this.doctor1);
		this.patient2.setName("Pedro");
		this.patient2.setNss("12345778S");
		this.patient2.setPassword("patient1");
		this.patient2.setPhone("123456789");
		this.patient2.setState("España");
		this.patient2.setSurname("Roldán");
		this.patient2.setUsername("patient1");

		this.appointment1 = new Appointment();
		this.appointment1.setId(1);
		this.appointment1.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 0));
		this.appointment1.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 0).plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.appointment2 = new Appointment();
		this.appointment2.setId(2);
		this.appointment2.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 21));
		this.appointment2.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 28));
		this.appointment2.setPatient(this.patient2);
		this.appointment2.setPriority(false);

		this.admin = new User();
		this.admin.setId(1);
		this.admin.setUsername("admin");
		this.admin.setPassword("admin");
		this.admin.setDni("11111111A");
		this.admin.setEmail("maria@gmail.com");
		this.admin.setEnabled(true);
		this.admin.setName("Maria");
		this.admin.setSurname("Sanchez Noriega-Cruz");
		this.admin.setPhone("666666666");


		Service service1 = new Service();
		service1.setId(3);
		service1.setName("Consulta Niños");

		Service service2 = new Service();
		service2.setId(4);
		service2.setName("Vacunación de la Gripe");

		this.services = new ArrayList<Service>();
		this.services.add(service1);

		this.doctor1.setServices(this.services);

		List<Doctor> doctors = new ArrayList<>();
		doctors.add(this.doctor1);

		List<LocalDateTime> appointments = new ArrayList<>();
		appointments.add(this.appointment1.getStartTime());
		appointments.add(this.appointment2.getStartTime());

		List<Patient> patients = new ArrayList<>();
		patients.add(this.patient1);
		patients.add(this.patient2);



		BDDMockito.given(this.patientService.findPatients()).willReturn(patients);
		BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(doctors);
		BDDMockito.given(this.appointmentService.findAppointmentByDoctors(1))
				.willReturn(appointments);
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findPatientById(2)).willReturn(this.patient1);
		BDDMockito.given(this.patientService.findDoctorByPatient(1)).willReturn(this.doctor1);
		BDDMockito.given(this.patientService.findDoctorByPatient(-1)).willReturn(null);
		BDDMockito.given(this.patientService.findPatientById(9)).willReturn(this.patient9);
	}


	@Test
	@WithMockUser(username = "admin", roles = {"admin"})
	void shouldListPatients() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patients"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/patients/patientsList"));
	}


	@Test
	@WithMockUser(username = "admin", roles = {"admin"})
	void shouldNotListPatients() throws Exception {

		List<Patient> patients = new ArrayList<>();
		BDDMockito.given(this.patientService.findPatients()).willReturn(patients);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/patients/patientsList"));
	}


	@Test
	@WithMockUser(username = "admin", roles = {"admin"})
	void shouldShowPatientDetails() throws Exception {

		BDDMockito.given(this.patientService.findPatientById(2)).willReturn(this.patient2);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}", 2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/admin/patients/patientDetails"));

	}

	@Test
	@WithMockUser(username = "admin", roles = {"admin"})
	void shouldNotShowPatientDetails() throws Exception {

		BDDMockito.given(this.patientService.findPatientById(-2)).willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}", -2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}

	@WithMockUser(username = "admin", roles = "admin")
	@Test
	void shouldDeleteDoctor() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}/delete", 9))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/patients"));
	}

	@WithMockUser(username = "admin", roles = "admin")
	@Test
	void shouldNotDeleteDoctorWithConsultations() throws Exception {
		BDDMockito.willThrow(DeletePatientException.class).given(this.patientService)
				.delete(this.patient5);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/patients/{patientId}/delete", 5))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/patients"));
	}


}
