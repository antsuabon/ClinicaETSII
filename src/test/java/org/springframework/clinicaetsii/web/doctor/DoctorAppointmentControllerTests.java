package org.springframework.clinicaetsii.web.doctor;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.projection.AppointmentPatient;
import org.springframework.clinicaetsii.model.projection.AppointmentPatientImpl;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DoctorAppointmentController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class DoctorAppointmentControllerTests {

	@Autowired
	private DoctorAppointmentController doctorAppointmentController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppointmentService appointmentService;

	private static final int TEST_APPOINTMENT_ID_1 = 1;
	private static final int TEST_APPOINTMENT_ID_2 = 2;

	private Doctor doctor1;
	private Patient patient1;

	private Appointment appointment1;
	private Appointment appointment2;

	private AppointmentPatientImpl appointmentPatient1;
	private AppointmentPatientImpl appointmentPatient2;

	@BeforeEach
	void initAppointments() {


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
		this.patient1.setBirthDate(LocalDate.of(1998, 8, 12));
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
		this.appointment1.setId(TEST_APPOINTMENT_ID_1);
		this.appointment1.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 0));
		this.appointment1.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 0).plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.appointment2 = new Appointment();
		this.appointment2.setId(TEST_APPOINTMENT_ID_2);
		this.appointment2.setStartTime(LocalDateTime.of(2020, 3, 25, 9, 21));
		this.appointment2.setEndTime(LocalDateTime.of(2020, 3, 25, 9, 28));
		this.appointment2.setPatient(this.patient1);
		this.appointment2.setPriority(false);

		Collection<Appointment> appointments = new ArrayList<Appointment>();
		appointments.add(this.appointment1);
		appointments.add(this.appointment2);

		this.appointmentPatient1 = new AppointmentPatientImpl();
		this.appointmentPatient1.setPatientId(this.appointment1.getPatient().getId());
		this.appointmentPatient1.setName(this.appointment1.getPatient().getName());
		this.appointmentPatient1.setSurname(this.appointment1.getPatient().getSurname());
		this.appointmentPatient1.setAppointmentId(this.appointment1.getId());
		this.appointmentPatient1.setStartTime(this.appointment1.getStartTime());
		this.appointmentPatient1.setEndTime(this.appointment1.getEndTime());

		this.appointmentPatient2 = new AppointmentPatientImpl();
		this.appointmentPatient2.setPatientId(this.appointment2.getPatient().getId());
		this.appointmentPatient2.setName(this.appointment2.getPatient().getName());
		this.appointmentPatient2.setSurname(this.appointment2.getPatient().getSurname());
		this.appointmentPatient2.setAppointmentId(this.appointment2.getId());
		this.appointmentPatient2.setStartTime(this.appointment2.getStartTime());
		this.appointmentPatient2.setEndTime(this.appointment2.getEndTime());

		Collection<AppointmentPatient> appointmentPatients = new ArrayList<>();
		appointmentPatients.add(this.appointmentPatient1);
		appointmentPatients.add(this.appointmentPatient2);

		given(this.appointmentService.findCurrentDoctorAppointments()).willReturn(appointments);
		given(this.appointmentService.findCurrentDoctorAppointmentsWithPatient())
				.willReturn(appointmentPatients);

	}

	@Test
	@WithMockUser(username = "doctor1", roles = "doctor")
	void doctorShouldListAppointments() throws Exception {
		this.mockMvc.perform(get("/doctor/appointments")).andExpect(status().isOk())
				.andExpect(model().attributeExists("appointments"))
				.andExpect(view().name("/doctor/consultations/appointmentsList"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = "doctor")
	void doctorShouldListEmptyAppointments() throws Exception {
		given(this.appointmentService.findCurrentDoctorAppointmentsWithPatient())
				.willReturn(new ArrayList<>());

		this.mockMvc.perform(get("/doctor/appointments")).andExpect(status().isOk())
				.andExpect(model().attributeExists("emptyList"))
				.andExpect(view().name("/doctor/consultations/appointmentsList"));
	}

	@Test
	@WithMockUser(value = "spring")
	void anyOtherUserShouldNotListAppointments() throws Exception {
		given(this.appointmentService.findCurrentDoctorAppointmentsWithPatient())
				.willThrow(AccessDeniedException.class);

		this.mockMvc.perform(get("/doctor/appointments")).andExpect(status().isOk())
				.andExpect(view().name("exception"));
	}


}
