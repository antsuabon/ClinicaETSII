package org.springframework.clinicaetsii.web.administrative;

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
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AdministrativeDoctorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class AdministrativeDoctorControllerTests {
	
	private static final int TEST_DOCTOR_ID_1 = 1;
	private static final int TEST_DOCTOR_ID_2 = 2;
	private static final int TEST_DOCTOR_ID_3 = 3;
	
	private static final int TEST_PATIENT_ID_1 = 1;
	private static final int TEST_PATIENT_ID_2 = 2;
	
	private static final int TEST_CONSULTATION_ID_1 = 1;
	
	private static final int TEST_APPOINTMENT_ID_1 = 1;
	
	private static final int TEST_PRESCRIPTION_ID_1 = 1;
	
	@MockBean
	private ConsultationService consultationService;
	
	@MockBean
	private PrescriptionService prescriptionService;
	
	@MockBean
	private DoctorService doctorService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AdministrativeDoctorController administrativeDoctorController;
	
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
	
	@BeforeEach
	void setup() {
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
