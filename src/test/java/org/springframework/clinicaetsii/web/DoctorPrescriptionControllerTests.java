package org.springframework.clinicaetsii.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.web.doctor.DoctorPrescriptionController;
import org.springframework.clinicaetsii.web.formatter.LocalDateTimeFormatter;
import org.springframework.clinicaetsii.web.formatter.MedicineFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorPrescriptionController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MedicineFormatter.class), excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class DoctorPrescriptionControllerTests {
	
	
	private static final int TEST_MEDICINE_ID_1	= 1;
	private static final int TEST_MEDICINE_ID_2	= 2;
	
	private static final int TEST_PATIENT_ID_1 = 4;
	
	private static final int TEST_DOCTOR_ID_1 = 1;
	
	private static final int TEST_SERVICE_ID_1 = 2; 
	
	@Autowired
	private DoctorPrescriptionController doctorPrescriptionController;

	@MockBean
	private PrescriptionService prescriptionService;
	
	@MockBean
	private PatientService patientService;
	
	@MockBean
	private DoctorService doctorService;
	
	@MockBean
	private MedicineService medicineService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private MockMvc	mockMvc;
	
	private Medicine medicine1;
	private Medicine medicine2;
	
	private List<Medicine> medicines;
	
	private Patient patient1;
	
	private Doctor doctor1;
	
	private Service service;
	
	void setup() {
		
		this.medicine1 = new Medicine();
		this.medicine1.setId(DoctorPrescriptionControllerTests.TEST_MEDICINE_ID_1);
		this.medicine1.setCommercialName("Ibuprofeno");
		this.medicine1.setGenericalName("Dalsy");
		this.medicine1.setQuantity(1f);
		this.medicine1.setContraindications("Dolor leve y moderado");
		this.medicine1.setIndications("En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.");

		this.medicine2 = new Medicine();
		this.medicine2.setId(DoctorPrescriptionControllerTests.TEST_MEDICINE_ID_2);
		this.medicine2.setCommercialName("Paracetamol");
		this.medicine2.setGenericalName("Paracel");
		this.medicine2.setQuantity(1f);
		this.medicine2.setContraindications("Dolor y fiebre");
		this.medicine2.setIndications("Paracetamol debe utilizarse con precaución en alcohólicos crónicos y en \n" + "pacientes con deficiencia en glucosa-6 fosfato-deshidrogenasa.");

		medicines = new ArrayList<>();
		medicines.add(medicine1);
		medicines.add(medicine2);
		
		service = new Service();
		service.setId(DoctorPrescriptionControllerTests.TEST_SERVICE_ID_1);
		service.setName("Consulta Adultos");
		
		List<Service> services = new ArrayList<>();
		services.add(service);
		
		doctor1 = new Doctor();
		doctor1.setId(DoctorPrescriptionControllerTests.TEST_DOCTOR_ID_1);
		doctor1.setUsername("doctor1");
		doctor1.setPassword("doctor1");
		doctor1.setEnabled(true);
		doctor1.setName("Pablo");
		doctor1.setSurname("Rodriguez Garrido");
		doctor1.setDni("12345678N");
		doctor1.setEmail("pablo@gmail.com");
		doctor1.setPhone("956784325");
		doctor1.setServices(services);
		doctor1.setCollegiateCode("303012345");
		
		patient1 = new Patient();
		patient1.setId(DoctorPrescriptionControllerTests.TEST_PATIENT_ID_1);
		patient1.setUsername("patient1");
		patient1.setPassword("patient1");
		patient1.setEnabled(true);
		patient1.setName("Alejandro");
		patient1.setSurname("Sánchez Saavedra");
		patient1.setDni("12345678N");
		patient1.setEmail("alejandro@gmail.com");
		patient1.setPhone("956784225");
		patient1.setNss("12345678S");
		patient1.setBirthDate(LocalDate.of(1982, 2, 22));
		patient1.setPhone2("953333333");
		patient1.setAddress("C/Calle de ejemplo");
		patient1.setState("Sevilla");
		patient1.setGeneralPractitioner(doctor1);
		
		
	}
	
	@Test
	@WithMockUser(username = "doctor1", roles = {
			"doctor"
	})
	void shouldInitCreatePrescriptionForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/prescriptions/new", TEST_PATIENT_ID_1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("prescription"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/prescriptions/createPrescriptionForm"));
	}
	
	@Test
	@WithMockUser(username = "doctor1", roles = {
		"doctor"
	})
	void shouldProcessCreatePrescriptionForm() throws Exception {
		setup();
		BDDMockito.given(this.patientService.findPatientById(TEST_PATIENT_ID_1)).willReturn(this.patient1);
		BDDMockito.given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor1);
		BDDMockito.given(this.medicineService.findMedicineById(TEST_MEDICINE_ID_1)).willReturn(this.medicine1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patients/{patientId}/prescriptions/new", TEST_PATIENT_ID_1)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("dosage", "3")
				.param("days", "3")
				.param("pharmaceuticalWarning", "None")
				.param("patientWarning", "None")
				.param("medicine", "1"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor/patients/{patientId}/prescriptions"));

	}

}
