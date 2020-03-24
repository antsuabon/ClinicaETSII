package org.springframework.clinicaetsii.web;

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
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.model.Diagnosis;
import org.springframework.clinicaetsii.model.DischargeType;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.ExaminationService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.doctor.DoctorExaminationController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = DoctorExaminationController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class DoctorExaminationControllerTest {

	@Autowired
	private DoctorExaminationController	doctorExaminationController;

	@MockBean
	private ExaminationService			examinationService;
	@MockBean
	private ConsultationService			consultationService;
	@MockBean
	private AppointmentService			appointmentService;
	@MockBean
	private PatientService				patientService;
	@MockBean
	private DoctorService				doctorService;

	@Autowired
	private MockMvc					mockMvc;

	FormattingConversionService cs;

	
	private Examination					examination1;
	private int						TEST_EXAMINATION_ID_1	= 1;
	
	private Consultation					consultation1;
	private int						TEST_CONSULTATION_ID_1	= 1;
	
	private Appointment					appointment1;
	private int						TEST_APPOINTMENT_ID_1	= 1;
	
	private Patient					patient1;
	private int						TEST_PATIENT_ID_1	= 1;
	
	private Doctor					doctor1;
	private int						TEST_DOCTOR_ID_1	= 1;


	@BeforeEach
	void setup() {
		
		
		this.examination1 = new Examination();
		this.examination1.setDescription("examination1");
		this.examination1.setId(TEST_EXAMINATION_ID_1);
		this.examination1.setStartTime(LocalDateTime.now());
		
		List<Examination> examinations = new ArrayList<>();
		examinations.add(this.examination1);
		
		this.doctor1 = new Doctor();
		this.doctor1.setId(this.TEST_DOCTOR_ID_1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Pablo");
		this.doctor1.setSurname("Rodriguez Garrido");
		this.doctor1.setDni("45612378P");
		this.doctor1.setEmail("pablo@gmail.com");
		this.doctor1.setPhone("955668756");
		this.doctor1.setCollegiateCode("303092345");
		
		this.patient1 = new Patient();
		this.patient1.setId(TEST_PATIENT_ID_1);
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
		this.appointment1.setEndTime(LocalDateTime.of(2022,12,12,12,12));
		this.appointment1.setId(TEST_APPOINTMENT_ID_1);
		this.appointment1.setPatient(this.patient1);
		this.appointment1.setPriority(true);
		this.appointment1.setStartTime(LocalDateTime.now());
		
		List<Constant> constants = new ArrayList<>();
		Constant a = new Constant();
		ConstantType x = new ConstantType();
		x.setId(1);
		x.setName("x");
		a.setConstantType(x);
		a.setId(1);
		a.setValue(12);
		constants.add(a);
		
		List<Diagnosis> diagnosis = new ArrayList<>();
		Diagnosis b = new Diagnosis();
		b.setId(1);
		b.setName("b");
		diagnosis.add(b);
		
		DischargeType dischargeType = new DischargeType();
		dischargeType.setId(1);
		dischargeType.setName("dischargeType");
		
		this.consultation1 = new Consultation();
		this.consultation1.setAnamnesis("amnesis");
		this.consultation1.setAppointment(this.appointment1);
		this.consultation1.setConstants(constants);
		this.consultation1.setDiagnoses(diagnosis);
		this.consultation1.setDischargeType(dischargeType);
		this.consultation1.setEndTime(LocalDateTime.of(2022,12,12,12,12));
		this.consultation1.setExaminations(examinations);
		this.consultation1.setId(TEST_CONSULTATION_ID_1);
		this.consultation1.setRemarks("remarks");
		this.consultation1.setStartTime(LocalDateTime.now());
		
		
		
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowExaminationPatient() throws Exception {
		
		this.setup();
		BDDMockito.given(this.examinationService.findExaminationsById(TEST_EXAMINATION_ID_1)).willReturn(this.examination1);
		this.mockMvc.perform(MockMvcRequestBuilders
			.get("/doctor/patients/{patientId}/consultations/{consultationId}/examinations/{examinationId}", TEST_PATIENT_ID_1 ,TEST_CONSULTATION_ID_1, TEST_EXAMINATION_ID_1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("examination"))
			.andExpect(MockMvcResultMatchers.view().name("doctor/examinations/examinationDetails"));
	}


	
}