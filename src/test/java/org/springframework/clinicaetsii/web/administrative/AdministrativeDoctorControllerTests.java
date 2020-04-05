
package org.springframework.clinicaetsii.web.administrative;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdministrativeDoctorController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LocalDateTimeFormatter.class),
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdministrativeDoctorControllerTests {

	private static final int				TEST_DOCTOR_ID_1	= 1;
	private static final int				TEST_DOCTOR_ID_2	= 2;

	@MockBean
	private DoctorService					doctorService;

	@Autowired
	private AdministrativeDoctorController	administrativeDoctorController;

	@MockBean
	private UserService						userService;

	@MockBean
	private AuthoritiesService				authoritiesService;

	@Autowired
	private MockMvc							mockMvc;

	private Doctor							doctor1;

	private Doctor							doctor2;

	private List<Doctor>					doctors;

	private static final int TEST_DOCTOR_ID_1 = 1;
	private static final int TEST_DOCTOR_ID_2 = 2;
	private static final int TEST_DOCTOR_ID_3 = 3;

	private static final int TEST_PATIENT_ID_1 = 1;
	private static final int TEST_PATIENT_ID_2 = 2;

	private static final int TEST_CONSULTATION_ID_1 = 1;

	private static final int TEST_APPOINTMENT_ID_1 = 1;

	private static final int TEST_PRESCRIPTION_ID_1 = 1;


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

		this.doctor2 = new Doctor();
		this.doctor2.setId(3);
		this.doctor2.setUsername("doctor3");
		this.doctor2.setPassword("doctor3");
		this.doctor2.setEnabled(true);
		this.doctor2.setName("Antonio");
		this.doctor2.setSurname("Suarez Bono");
		this.doctor2.setDni("45612378P");
		this.doctor2.setEmail("antonio@gmail.com");
		this.doctor2.setPhone("955668756");
		this.doctor2.setCollegiateCode("303024345");

		List<Doctor> doctors = new ArrayList<>();
		doctors.add(this.doctor1);
		doctors.add(this.doctor2);


		private Doctor doctor1;
		private Doctor doctor2;
		private Doctor doctor3;

		private Patient patient1;
		private Patient patient2;

		private Prescription prescription1;

		private Consultation consultation1;

		private Appointment appointment1;

		private Collection<Consultation> consultations;
		private Collection<Prescription> prescriptions;

			doctor1 = new Doctor();
			doctor1.setId(TEST_DOCTOR_ID_1);

			doctor2 = new Doctor();
			doctor2.setId(TEST_DOCTOR_ID_2);

			doctor3 = new Doctor();
			doctor3.setId(TEST_DOCTOR_ID_3);

			patient1 = new Patient();
			patient1.setId(TEST_PATIENT_ID_1);
			patient1.setGeneralPractitioner(doctor2);

			patient2 = new Patient();
			patient2.setId(TEST_PATIENT_ID_2);
			patient2.setGeneralPractitioner(doctor3);

			appointment1 = new Appointment();
			appointment1.setId(TEST_APPOINTMENT_ID_1);
			appointment1.setPatient(patient1);

			consultation1 = new Consultation();
			consultation1.setId(TEST_CONSULTATION_ID_1);
			consultation1.setAppointment(appointment1);

			consultations = new ArrayList<>();
			consultations.add(consultation1);

			prescription1 = new Prescription();
			prescription1.setId(TEST_PRESCRIPTION_ID_1);
			prescription1.setDoctor(doctor3);
			prescription1.setPatient(patient2);

			prescriptions = new ArrayList<>();
			prescriptions.add(prescription1);


		BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(doctors);
		BDDMockito.given(this.doctorService.findDoctorById(AdministrativeDoctorControllerTests.TEST_DOCTOR_ID_1)).willReturn(this.doctor1);
		BDDMockito.given(this.doctorService.findDoctorById(AdministrativeDoctorControllerTests.TEST_DOCTOR_ID_2)).willReturn(this.doctor2);
	}

	@WithMockUser(username = "administrative1", roles = {
		"administrative"
	})
	@Test
	void testListDoctors() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/doctors")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("doctors"))
			.andExpect(MockMvcResultMatchers.view().name("/administrative/doctors/doctorsList"));
	}

	@WithMockUser(username = "administrative1", roles = {
		"administrative"
	})
	@Test
	void testNotListDoctors() throws Exception {
		List<Doctor> doctors = new ArrayList<>();
		BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(doctors);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/doctors")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
			.andExpect(MockMvcResultMatchers.view().name("/administrative/doctors/doctorsList"));
	}

	@WithMockUser(username = "administrative1", roles = {"administrative"})
	@Test
	void testFindDoctorById() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/administrative/doctors/{doctorId}",AdministrativeDoctorControllerTests.TEST_DOCTOR_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/doctors/doctorDetails"));
	}

	@WithMockUser(username = "administrative1", roles = {"administrative"})
	@Test
	void shouldNotShowDoctor() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/administrative/doctors/{doctorId}",-1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "administrative1", roles = "administrative")
	@Test
	void shouldDeleteDoctor() throws Exception {
		BDDMockito.given(this.consultationService.findAllConsultationsFromDoctor(TEST_DOCTOR_ID_1)).willReturn(new ArrayList<>());
		BDDMockito.given(this.prescriptionService.findAllPrescriptionsByDoctor(TEST_DOCTOR_ID_1)).willReturn(new ArrayList<>());

		this.mockMvc
			.perform(MockMvcRequestBuilders
				.get("/administrative/doctors/{doctorId}/delete", TEST_DOCTOR_ID_1))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/administrative/doctors/"));
	}

	@WithMockUser(username = "administrative1", roles = "administrative")
	@Test
	void shouldNotDeleteDoctorWithConsultations() throws Exception {
		BDDMockito.given(this.consultationService.findAllConsultationsFromDoctor(TEST_DOCTOR_ID_1)).willReturn(consultations);
		BDDMockito.given(this.prescriptionService.findAllPrescriptionsByDoctor(TEST_DOCTOR_ID_1)).willReturn(new ArrayList<>());

		this.mockMvc
			.perform(MockMvcRequestBuilders
				.get("/administrative/doctors/{doctorId}/delete", TEST_DOCTOR_ID_1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "administrative1", roles = "administrative")
	@Test
	void shouldNotDeleteDoctorWithPrescriptions() throws Exception {
		BDDMockito.given(this.consultationService.findAllConsultationsFromDoctor(TEST_DOCTOR_ID_1)).willReturn(new ArrayList<>());
		BDDMockito.given(this.prescriptionService.findAllPrescriptionsByDoctor(TEST_DOCTOR_ID_1)).willReturn(prescriptions);

		this.mockMvc
			.perform(MockMvcRequestBuilders
				.get("/administrative/doctors/{doctorId}/delete", TEST_DOCTOR_ID_1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exception"));
	}


}
