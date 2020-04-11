
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
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.service.exceptions.DeleteDoctorException;
import org.springframework.clinicaetsii.web.admin.AdminDoctorController;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdminDoctorController.class,
		includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = LocalDateTimeFormatter.class),
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminDoctorControllerTests {

	@MockBean
	private DoctorService doctorService;

	@Autowired
	private AdminDoctorController administrativeDoctorController;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private ConsultationService consultationService;

	@MockBean
	private PrescriptionService prescriptionService;

	@Autowired
	private MockMvc mockMvc;

	private Doctor doctor1;
	private Doctor doctor2;
	private Doctor doctor3;

	private List<Doctor> doctors;

	private Patient patient1;
	private Patient patient2;

	private Prescription prescription1;
	private Collection<Prescription> prescriptions;

	private Appointment appointment1;

	private Consultation consultation1;
	private Collection<Consultation> consultations;

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

		this.doctors = new ArrayList<>();
		this.doctors.add(this.doctor1);
		this.doctors.add(this.doctor2);

		this.doctor3 = new Doctor();
		this.doctor3.setId(TEST_DOCTOR_ID_3);

		this.patient1 = new Patient();
		this.patient1.setId(TEST_PATIENT_ID_1);
		this.patient1.setGeneralPractitioner(this.doctor2);

		this.patient2 = new Patient();
		this.patient2.setId(TEST_PATIENT_ID_2);
		this.patient2.setGeneralPractitioner(this.doctor3);

		this.appointment1 = new Appointment();
		this.appointment1.setId(TEST_APPOINTMENT_ID_1);
		this.appointment1.setPatient(this.patient1);

		this.consultation1 = new Consultation();
		this.consultation1.setId(TEST_CONSULTATION_ID_1);
		this.consultation1.setAppointment(this.appointment1);

		this.consultations = new ArrayList<>();
		this.consultations.add(this.consultation1);

		this.prescription1 = new Prescription();
		this.prescription1.setId(TEST_PRESCRIPTION_ID_1);
		this.prescription1.setDoctor(this.doctor3);
		this.prescription1.setPatient(this.patient2);

		this.prescriptions = new ArrayList<>();
		this.prescriptions.add(this.prescription1);


		BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(this.doctors);
		BDDMockito
				.given(this.doctorService
						.findDoctorById(AdminDoctorControllerTests.TEST_DOCTOR_ID_1))
				.willReturn(this.doctor1);
		BDDMockito
				.given(this.doctorService
						.findDoctorById(AdminDoctorControllerTests.TEST_DOCTOR_ID_2))
				.willReturn(this.doctor2);
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void testListDoctors() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctors"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/doctors/doctorsList"));
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void testNotListDoctors() throws Exception {
		List<Doctor> doctors = new ArrayList<>();
		BDDMockito.given(this.doctorService.findAllDoctors()).willReturn(doctors);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/doctors/doctorsList"));
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void testFindDoctorById() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}",
						AdminDoctorControllerTests.TEST_DOCTOR_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("/admin/doctors/doctorDetails"));
	}

	@WithMockUser(username = "admin", roles = {"admin"})
	@Test
	void shouldNotShowDoctor() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}", -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("doctor"))
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", roles = "admin")
	@Test
	void shouldDeleteDoctor() throws Exception {

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}/delete",
						TEST_DOCTOR_ID_1))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/admin/doctors"));
	}

	@WithMockUser(username = "admin", roles = "admin")
	@Test
	void shouldNotDeleteDoctorWithConsultations() throws Exception {
		BDDMockito.willThrow(DeleteDoctorException.class).given(this.doctorService)
				.delete(this.doctor1);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}/delete",
						TEST_DOCTOR_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(username = "admin", roles = "admin")
	@Test
	void shouldNotDeleteDoctorWithPrescriptions() throws Exception {
		BDDMockito.willThrow(DeleteDoctorException.class).given(this.doctorService)
				.delete(this.doctor1);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/admin/doctors/{doctorId}/delete",
						TEST_DOCTOR_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}


}
