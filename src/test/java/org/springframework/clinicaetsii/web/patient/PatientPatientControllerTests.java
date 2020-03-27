package org.springframework.clinicaetsii.web.patient;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.formatter.DoctorFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PatientPatientController.class,
		includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = DoctorFormatter.class),
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)

public class PatientPatientControllerTests {

	@Autowired
	private PatientPatientController patientPatientController;

	@MockBean
	private PatientService patientService;

	@MockBean
	private DoctorFormatter doctorFormatter;

	@MockBean
	private DoctorService doctorService;

	@Autowired
	private MockMvc mockMvc;

	private Patient patient1;

	private Patient patient2;

	private Doctor doctor1;

	private Doctor doctor3;

	private Doctor doctor4;

	Collection<Doctor> doctors;

	private Appointment appointment1;

	private Appointment appointment2;

	private DischargeType dischargeType1;

	private DischargeType dischargeType2;

	private Consultation consultation1;

	private Consultation consultation2;

	@BeforeEach
	void setup() {

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

		this.consultation2 = new Consultation();
		this.consultation2.setId(2);
		this.consultation2.setStartTime(LocalDateTime.now().plusMinutes(7));
		this.consultation2.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.consultation2.setAnamnesis("Dolor de rodilla");
		this.consultation2.setRemarks("Inflamación");
		this.dischargeType2 = new DischargeType();
		this.dischargeType2.setId(2);
		this.dischargeType2.setName("Especialidad");
		this.consultation2.setDischargeType(this.dischargeType2);
		this.consultation2.setAppointment(this.appointment2);


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

		this.doctor4 = new Doctor();
		this.doctor4.setId(4);
		this.doctor4.setUsername("doctor4");
		this.doctor4.setPassword("doctor4");
		this.doctor4.setEnabled(true);
		this.doctor4.setName("Juan");
		this.doctor4.setSurname("Lopez Lopez");
		this.doctor4.setDni("45345678P");
		this.doctor4.setEmail("Juan@gmail.com");
		this.doctor4.setPhone("987654321");
		this.doctor4.setCollegiateCode("938475610");

		this.patient1 = new Patient();
		this.patient1.setId(1);
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
		this.patient2.setId(2);
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

		this.doctors = new ArrayList<>();
		this.doctors.add(this.doctor3);
		this.doctors.add(this.doctor4);
	}



	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void shouldShowPatient() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"))
				.andExpect(MockMvcResultMatchers.view().name("patient/patientDetails"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void doctorShouldNotShowPatient() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patient"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void administrativeShouldNotShowPatient() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patient"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotShowPatient() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patient"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}



	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void shouldInitUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientForm"))
				.andExpect(MockMvcResultMatchers.view().name("/patient/updatePatientForm"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void doctorShouldNotInitUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patientForm"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void administrativeShouldNotInitUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patientForm"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotInitUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("patientForm"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}


	@Test
	@WithMockUser(username = "patient1", roles = {"patient"})
	void shouldNotProcessUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/patient/edit")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("patient.name", "Pablo").param("patient.surname", "")
						.param("patient.dni", "").param("patient.email", "pablo@gmail.com")
						.param("patient.phone", "955668756").param("patient.username", "patient2")
						.param("newPassword", "").param("repeatPassword", ""))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void doctorShouldNotProcessUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit").with(csrf())
				.param("patient.name", "Pablo").param("patient.surname", "Rodriguez Garrido")
				.param("patient.dni", "45612378P").param("patient.email", "pablo@gmail.com")
				.param("patient.phone", "955668756").param("patient.username", "patient2")
				.param("newPassword", "").param("repeatPassword", "")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "administrative", roles = {"administrative"})
	void administrativeShouldNotProcessUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("patient.name", "Pablo")
				.param("patient.surname", "Rodriguez Garrido").param("patient.dni", "45612378P")
				.param("patient.email", "pablo@gmail.com").param("patient.phone", "955668756")
				.param("patient.username", "patient2").param("newPassword", "")
				.param("repeatPassword", "")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotProcessUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());

		this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/edit")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("patient.name", "Pablo")
				.param("patient.surname", "Rodriguez Garrido").param("patient.dni", "45612378P")
				.param("patient.email", "pablo@gmail.com").param("patient.phone", "955668756")
				.param("patient.username", "patient2").param("newPassword", "")
				.param("repeatPassword", "")).andExpect(status().isOk())
				.andExpect(view().name("exception"));
	}


	@Test
	@WithMockUser(username = "spring")
	void shouldNotInitUpdatePatientForm() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(new RuntimeException());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}


	// @Test
	// @WithMockUser(username = "patient1", roles = {"patient"})
	// void shouldProcessUpdatePatientForm() throws Exception {
	// BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
	// BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(this.doctors);
	// BDDMockito.given(this.doctorService.findDoctorById(4)).willReturn(this.doctor4);
	// this.mockMvc
	// .perform(MockMvcRequestBuilders.post("/patient/edit")
	// .with(SecurityMockMvcRequestPostProcessors.csrf())
	// .param("generalPractitioner", "4"))
	// .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	// .andExpect(MockMvcResultMatchers.view().name("redirect:/patient"));
	// }


}
