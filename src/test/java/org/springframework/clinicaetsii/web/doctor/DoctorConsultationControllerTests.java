
package org.springframework.clinicaetsii.web.doctor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.service.ExaminationService;
import org.springframework.clinicaetsii.web.formatter.DiagnosisFormatter;
import org.springframework.clinicaetsii.web.formatter.DischargeTypeFormatter;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorConsultationController.class,
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
						classes = LocalDateTimeFormatter.class),
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
						classes = DiagnosisFormatter.class),
				@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
						classes = DischargeTypeFormatter.class)},
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
class DoctorConsultationControllerTests {

	private static final int TEST_CONSULTATION_ID_1 = 1;
	private static final int TEST_CONSULTATION_ID_2 = 2;

	@MockBean
	private ConsultationService consultationService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private ExaminationService examinationService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DoctorConsultationController doctorConsultationController;

	private Consultation consultation1;
	private Consultation consultation2;

	private Doctor doctor1;

	private Patient patient1;

	private Appointment appointment1;
	private Appointment appointment2;

	private Examination examination1;

	private DischargeType dischargeType1;
	private DischargeType dischargeType2;


	private Collection<DischargeType> dischargeTypes;
	private Collection<Diagnosis> diagnoses;

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
		this.examination1 = new Examination();
		this.examination1.setId(1);
		this.examination1.setDescription("Tiene el vientre muy hinchado");
		Collection<Examination> examinations = new ArrayList<>();
		examinations.add(this.examination1);
		this.consultation1.setExaminations(examinations);

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

		this.dischargeTypes = new ArrayList<>();
		this.dischargeTypes.add(this.dischargeType1);
		this.dischargeTypes.add(this.dischargeType2);


		Diagnosis diagnosis1 = new Diagnosis();
		diagnosis1.setId(1);
		diagnosis1.setName("Gripe invernal");

		this.diagnoses = new ArrayList<>();
		this.diagnoses.add(diagnosis1);

		BDDMockito.given(this.consultationService.findAllDiagnoses()).willReturn(this.diagnoses);
		BDDMockito.given(this.consultationService.findDischargeTypes())
				.willReturn(this.dischargeTypes);
		BDDMockito
				.given(this.appointmentService.findAppointmentById(
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
				.willReturn(this.appointment1);

		Mockito.doAnswer(invocation -> {
			Consultation consultation = invocation.getArgument(0);

			if (consultation.getId() == null) {
				consultation.setId(5);
			}

			return null;
		}).when(this.consultationService).save(ArgumentMatchers.any(Consultation.class));

		BDDMockito.given(this.consultationService.findConsultationsByPatientId(1))
				.willReturn(consultations2);
		BDDMockito
				.given(this.consultationService.findFullConsultationById(
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
				.willReturn(this.consultation1);

		BDDMockito.given(this.appointmentService.findAppointmentById(1))
				.willReturn(this.appointment1);

		Mockito.doAnswer(invocation -> {
			Consultation consultation = invocation.getArgument(0);

			if (consultation.getId() == null) {
				consultation.setId(5);
			}

			return null;
		}).when(this.consultationService).save(ArgumentMatchers.any(Consultation.class));

		BDDMockito.given(this.consultationService.findAllDiagnoses()).willReturn(this.diagnoses);
		BDDMockito.given(this.consultationService.findDischargeTypes())
				.willReturn(this.dischargeTypes);
	}

	@WithMockUser(value = "spring")
	@Test
	void testListConsultationsPatient() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("consultations"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/consultationsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListConsultationsPatient() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations",
						-1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("emptyList"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/consultationsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDetailsConsultation() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(
						"/doctor/patients/{patientId}/consultations/{consultationId}",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1,
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("consultation"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/consultationDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotDetailsConsultation() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(
						"/doctor/patients/{patientId}/consultations/{consultationId}",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1, -1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("empty"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/consultationDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/doctor/patients/{patientId}/consultations/new",
								DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)
						.queryParam("appointmentId",
								String.valueOf(
										DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("consultation"))
				.andExpect(MockMvcResultMatchers.model().attribute("consultation",
						Matchers.hasProperty("startTime")))
				.andExpect(MockMvcResultMatchers.model().attribute("consultation",
						Matchers.hasProperty("appointment", Matchers.is(this.appointment1))))
				.andExpect(MockMvcResultMatchers.view()
						.name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationForm() throws Exception {
		this.mockMvc
				.perform(post("/doctor/patients/{patientId}/consultations/new",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)
								.with(csrf())
								.param("startTime",
										this.appointment1.getStartTime().plusMinutes(5)
												.format(DateTimeFormatter.ISO_DATE_TIME))
								.param("appointmentId", String.valueOf(
										DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
								.param("anamnesis", this.consultation1.getAnamnesis())
								.param("remarks", this.consultation1.getRemarks()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/doctor/patients/{patientId}/consultations/5"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotProcessCreationForm() throws Exception {
		this.consultation1.getExaminations().clear();
		this.mockMvc
				.perform(post("/doctor/patients/{patientId}/consultations/new",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)
								.with(csrf())
								.param("startTime",
										this.appointment1.getStartTime().minusMinutes(5)
												.format(DateTimeFormatter.ISO_DATE_TIME))
								.param("appointmentId", String.valueOf(
										DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
								.param("anamnesis", "").param("remarks", ""))
				.andExpect(status().isOk()).andExpect(model().attributeExists("consultation"))
				.andExpect(model().attributeHasFieldErrors("consultation", "startTime"))
				.andExpect(view().name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitEditionForm() throws Exception {
		this.mockMvc
				.perform(get("/doctor/patients/{patientId}/consultations/{consultationId}/edit",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1,
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(model().attribute("consultation", this.consultation1))
				.andExpect(model().attribute("dischargeTypes", this.dischargeTypes))
				.andExpect(model().attribute("allDiagnoses", this.diagnoses))
				.andExpect(view().name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditionForm() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/consultations/{consultationId}/edit",
								DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1,
								DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime",
								this.appointment1.getStartTime().plusMinutes(5)
										.format(DateTimeFormatter.ISO_DATE_TIME))
						.param("appointmentId",
								String.valueOf(
										DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
						.param("anamnesis", this.consultation1.getAnamnesis())
						.param("remarks", this.consultation1.getRemarks()).param("diagnoses", "1")
						.param("dischargeType", "1"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditionFormWithoutDischargeType() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/doctor/patients/{patientId}/consultations/{consultationId}/edit",
								DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1,
								DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime",
								this.appointment1.getStartTime().plusMinutes(5)
										.format(DateTimeFormatter.ISO_DATE_TIME))
						.param("appointmentId",
								String.valueOf(
										DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
						.param("anamnesis", this.consultation1.getAnamnesis())
						.param("remarks", this.consultation1.getRemarks()).param("diagnoses", "1")
						.param("dischargeType", ""))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name(
						"redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotProcessEditionForm() throws Exception {
		this.consultation1.getExaminations().clear();
		this.mockMvc
				.perform(post("/doctor/patients/{patientId}/consultations/{consultationId}/edit",
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1,
						DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1)
								.with(csrf())
								.param("startTime",
										this.appointment1.getStartTime().plusMinutes(5)
												.format(DateTimeFormatter.ISO_DATE_TIME))
								.param("appointmentId", String.valueOf(
										DoctorConsultationControllerTests.TEST_CONSULTATION_ID_1))
								.param("anamnesis", "").param("remarks", "").param("diagnoses", "")
								.param("dischargeType", "1"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("consultation"))
				.andExpect(model().attributeHasFieldErrors("consultation", "dischargeType"))
				.andExpect(view().name("/doctor/consultations/createOrUpdateConsultationForm"));
	}

}
