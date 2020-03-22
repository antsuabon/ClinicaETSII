
package org.springframework.clinicaetsii.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.web.doctor.DoctorConsultationController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorConsultationController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

class DoctorConsultationControllerTests {

	private static final int				TEST_CONSULTATION_ID_1	= 1;
	private static final int				TEST_CONSULTATION_ID_2	= 2;

	@MockBean
	private ConsultationService				consultationService;

	@MockBean
	private AppointmentService				appointmentService;

	@MockBean
	private AuthoritiesService				authoritiesService;

	@Autowired
	private MockMvc							mockMvc;

	private Consultation					consultation1;
	private Consultation					consultation2;

	private Doctor							doctor1;

	private Patient							patient1;

	private Appointment						appointment1;
	private Appointment						appointment2;

	private DischargeType					dischargeType1;
	private DischargeType					dischargeType2;


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
		this.appointment1.setStartTime(LocalDateTime.now());
		this.appointment1.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(false);

		this.consultation1 = new Consultation();
		this.consultation1.setId(DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1);
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
		this.consultation2.setId(DoctorConsultationControllerTests.TEST_CONSULTATION_ID_2);
		this.consultation2.setStartTime(LocalDateTime.now().plusMinutes(7));
		this.consultation2.setEndTime(LocalDateTime.now().plusMinutes(7));
		this.consultation2.setAnamnesis("Dolor de rodilla");
		this.consultation2.setRemarks("Inflamación");
		this.dischargeType2 = new DischargeType();
		this.dischargeType2.setId(2);
		this.dischargeType2.setName("Especialidad");
		this.consultation2.setDischargeType(this.dischargeType2);
		this.consultation2.setAppointment(this.appointment2);

		List<Consultation> consultations = new ArrayList<>();

		consultations.add(this.consultation1);
		consultations.add(this.consultation2);

		Collection<Consultation> consultations2 = consultations;

		BDDMockito.given(this.consultationService.findConsultationsByPatientId(DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
			.willReturn(consultations2);
		BDDMockito.given(this.consultationService.findConsultationById(DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
			.willReturn(this.consultation1);

	}

	@WithMockUser(value = "spring")
	@Test
	void testListConsultationsPatient() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders
			.get("/doctor/patients/{patientId}/consultations", DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("consultations"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/consultationsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListConsultationsPatient() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations", -1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/consultationsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDetailsConsultation() throws Exception {
		this.setup();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations/{consultationId}", DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1, DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("consultation"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/consultationDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotDetailsConsultation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations/{consultationId}", DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1, -1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/consultationDetails"));
	}

}
