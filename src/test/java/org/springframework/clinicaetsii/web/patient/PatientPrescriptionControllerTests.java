package org.springframework.clinicaetsii.web.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.web.PatientPrescriptionController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PatientPrescriptionController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class PatientPrescriptionControllerTests {

	private static final int TEST_PRESCRIPTION_ID_1 = 1;
	private static final int TEST_PRESCRIPTION_ID_2 = 2;

	private static final int TEST_MEDICINE_ID_1 = 1;
	private static final int TEST_MEDICINE_ID_2 = 2;

	private static final int TEST_PATIENT_ID_1 = 4;

	private static final int TEST_DOCTOR_ID_1 = 1;

	private static final int TEST_SERVICE_ID_1 = 2;

	@Autowired
	private PatientPrescriptionController prescriptionController;

	@MockBean
	private PrescriptionService prescriptionService;

	@MockBean
	private PatientService patientService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Medicine medicine1;
	private Medicine medicine2;

	private Patient patient1;

	private Doctor doctor1;

	private Service service;

	private Prescription prescription1;
	private Prescription prescription2;

	@BeforeEach
	void setup() {

		this.medicine1 = new Medicine();
		this.medicine1.setId(PatientPrescriptionControllerTests.TEST_MEDICINE_ID_1);
		this.medicine1.setCommercialName("Ibuprofeno");
		this.medicine1.setGenericalName("Dalsy");
		this.medicine1.setQuantity(1f);
		this.medicine1.setContraindications("Dolor leve y moderado");
		this.medicine1.setIndications(
				"En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.");

		this.medicine2 = new Medicine();
		this.medicine2.setId(PatientPrescriptionControllerTests.TEST_MEDICINE_ID_2);
		this.medicine2.setCommercialName("Paracetamol");
		this.medicine2.setGenericalName("Paracel");
		this.medicine2.setQuantity(1f);
		this.medicine2.setContraindications("Dolor y fiebre");
		this.medicine2.setIndications(
				"Paracetamol debe utilizarse con precaución en alcohólicos crónicos y en \n"
						+ "pacientes con deficiencia en glucosa-6 fosfato-deshidrogenasa.");

		this.service = new Service();
		this.service.setId(PatientPrescriptionControllerTests.TEST_SERVICE_ID_1);
		this.service.setName("Consulta Adultos");

		List<Service> services = new ArrayList<>();
		services.add(this.service);

		this.doctor1 = new Doctor();
		this.doctor1.setId(PatientPrescriptionControllerTests.TEST_DOCTOR_ID_1);
		this.doctor1.setUsername("doctor1");
		this.doctor1.setPassword("doctor1");
		this.doctor1.setEnabled(true);
		this.doctor1.setName("Pablo");
		this.doctor1.setSurname("Rodriguez Garrido");
		this.doctor1.setDni("12345678N");
		this.doctor1.setEmail("pablo@gmail.com");
		this.doctor1.setPhone("956784325");
		this.doctor1.setServices(services);
		this.doctor1.setCollegiateCode("303012345");

		this.patient1 = new Patient();
		this.patient1.setId(PatientPrescriptionControllerTests.TEST_PATIENT_ID_1);
		this.patient1.setUsername("patient1");
		this.patient1.setPassword("patient1");
		this.patient1.setEnabled(true);
		this.patient1.setName("Alejandro");
		this.patient1.setSurname("Sánchez Saavedra");
		this.patient1.setDni("12345678N");
		this.patient1.setEmail("alejandro@gmail.com");
		this.patient1.setPhone("956784225");
		this.patient1.setNss("12345678S");
		this.patient1.setBirthDate(LocalDate.of(1982, 2, 22));
		this.patient1.setPhone2("953333333");
		this.patient1.setAddress("C/Calle de ejemplo");
		this.patient1.setState("Sevilla");
		this.patient1.setGeneralPractitioner(this.doctor1);

		this.prescription1 = new Prescription();
		this.prescription1.setId(PatientPrescriptionControllerTests.TEST_PRESCRIPTION_ID_1);
		this.prescription1.setDays(7);
		this.prescription1.setDosage(1);
		this.prescription1.setStartDate(LocalDateTime.of(2020, 3, 9, 11, 0, 0));
		this.prescription1.setPharmaceuticalWarning("Vender solo con receta");
		this.prescription1.setPatientWarning("Puede provocar efectos secundarios");
		this.prescription1.setMedicine(this.medicine1);
		this.prescription1.setDoctor(this.doctor1);
		this.prescription1.setPatient(this.patient1);

		this.prescription2 = new Prescription();
		this.prescription2.setId(PatientPrescriptionControllerTests.TEST_PRESCRIPTION_ID_2);
		this.prescription2.setDays(20);
		this.prescription2.setDosage(3);
		this.prescription2.setStartDate(LocalDateTime.of(2020, 2, 20, 13, 0, 0));
		this.prescription2.setPharmaceuticalWarning("No tomar sin agua");
		this.prescription2.setPatientWarning("No fragmentar");
		this.prescription2.setMedicine(this.medicine2);
		this.prescription2.setDoctor(this.doctor1);
		this.prescription2.setPatient(this.patient1);

		List<Prescription> prescriptions = new ArrayList<>();
		prescriptions.add(this.prescription1);
		prescriptions.add(this.prescription2);

		BDDMockito.given(this.patientService.findCurrentPatient()).willReturn(this.patient1);
		BDDMockito.given(this.prescriptionService.findPrescriptionsByPatientUsername("patient1"))
				.willReturn(prescriptions);
		BDDMockito
				.given(this.prescriptionService.findPrescriptionById(
						PatientPrescriptionControllerTests.TEST_PRESCRIPTION_ID_1))
				.willReturn(this.prescription1);

	}

	@WithMockUser(value = "patient1", roles = "patient")
	@Test
	void testListPrescritionsByUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("prescriptions"))
				.andExpect(MockMvcResultMatchers.view().name("/prescriptions/prescriptionList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListPrescritionsByOtherUserRole() throws Exception {
		BDDMockito.given(this.patientService.findCurrentPatient())
				.willThrow(RuntimeException.class);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));
	}

	@WithMockUser(value = "patient1", roles = "patient")
	@Test
	void testShowPrescriptionById() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/prescriptions/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("consultation"))
				.andExpect(MockMvcResultMatchers.view().name("/prescriptions/prescriptionDetails"));
	}
}
