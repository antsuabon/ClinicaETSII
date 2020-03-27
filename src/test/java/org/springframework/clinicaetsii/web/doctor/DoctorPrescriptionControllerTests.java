
package org.springframework.clinicaetsii.web.doctor;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.AuthoritiesService;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.clinicaetsii.web.formatter.MedicineFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DoctorPrescriptionController.class,
		includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = MedicineFormatter.class),
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
class DoctorPrescriptionControllerTests {

	private static final int TEST_PATIENT_ID_1 = 1;

	@Autowired
	private DoctorPrescriptionController doctorPrescriptionController;

	@MockBean
	private PrescriptionService prescriptionService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private DoctorService doctorService;

	@MockBean
	private PatientService patientService;

	@MockBean
	private MedicineService medicineService;

	@Autowired
	private MockMvc mockMvc;

	private Patient patient1;

	private Doctor doctor3;

	private Prescription prescription1;

	private Prescription prescription2;

	private Medicine medicine1;

	private Medicine medicine2;

	@BeforeEach
	void setup() {

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

		this.patient1 = new Patient();
		this.patient1.setId(DoctorPrescriptionControllerTests.TEST_PATIENT_ID_1);
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

		this.medicine1 = new Medicine();
		this.medicine1.setId(1);
		this.medicine1.setCommercialName("Ibuprofeno");
		this.medicine1.setGenericalName("Dalsy");
		this.medicine1.setQuantity(1f);
		this.medicine1.setContraindications("Dolor leve y moderado");
		this.medicine1.setIndications(
				"En síndrome de pólipos nasales, angioedema y reactividad broncoespástica a aspirina u otros AINEs.");

		this.medicine2 = new Medicine();
		this.medicine2.setId(2);
		this.medicine2.setCommercialName("Paracetamol");
		this.medicine2.setGenericalName("Paracel");
		this.medicine2.setQuantity(1f);
		this.medicine2.setContraindications("Dolor y fiebre");
		this.medicine2.setIndications(
				"Paracetamol debe utilizarse con precaución en alcohólicos crónicos y en \n"
						+ "pacientes con deficiencia en glucosa-6 fosfato-deshidrogenasa.");

		this.prescription1 = new Prescription();
		this.prescription1.setId(1);
		this.prescription1.setDays(5);
		this.prescription1.setDoctor(this.doctor3);
		this.prescription1.setDosage(1);
		this.prescription1.setMedicine(this.medicine1);
		this.prescription1.setPatient(this.patient1);
		this.prescription1.setPatientWarning("Efectos secundarios");
		this.prescription1.setPharmaceuticalWarning("Solo con receta");
		this.prescription1.setStartDate(LocalDateTime.now());

		this.prescription2 = new Prescription();
		this.prescription2.setId(2);
		this.prescription2.setDays(7);
		this.prescription2.setDoctor(this.doctor3);
		this.prescription2.setDosage(1);
		this.prescription2.setMedicine(this.medicine2);
		this.prescription2.setPatient(this.patient1);
		this.prescription2.setPatientWarning("Efectos secundarios");
		this.prescription2.setPharmaceuticalWarning("Solo con receta");
		this.prescription2.setStartDate(LocalDateTime.now());

		List<Prescription> prescriptions = new ArrayList<>();

		prescriptions.add(this.prescription1);
		prescriptions.add(this.prescription2);

		Collection<Prescription> prescriptions2 = prescriptions;

		given(this.prescriptionService.findPrescriptionsByPatientId(1)).willReturn(prescriptions2);
		given(this.prescriptionService.findPrescriptionById(1)).willReturn(this.prescription1);
		given(this.patientService
				.findPatientById(DoctorPrescriptionControllerTests.TEST_PATIENT_ID_1))
						.willReturn(this.patient1);
		given(this.doctorService.findCurrentDoctor()).willReturn(this.doctor3);
		given(this.medicineService.findMedicineById(1)).willReturn(this.medicine1);
	}

	@WithMockUser(value = "spring")
	@Test
	void testListPrescriptions() throws Exception {
		this.mockMvc.perform(get("/doctor/patients/{patientId}/prescriptions", 1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("prescriptions"))
				.andExpect(model().attributeExists("patientId"))
				.andExpect(view().name("/doctor/prescriptions/prescriptionsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotListPrescriptions() throws Exception {
		given(this.prescriptionService.findPrescriptionsByPatientId(1))
				.willReturn(new ArrayList<>());
		this.mockMvc.perform(get("/doctor/patients/{patientId}/prescriptions", 1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("emptyList"))
				.andExpect(view().name("/doctor/prescriptions/prescriptionsList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void showPrescriptionDetails() throws Exception {
		this.mockMvc
				.perform(get("/doctor/patients/{patientId}/prescriptions/{prescriptionId}", 1, 1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("prescription"))
				.andExpect(view().name("/doctor/prescriptions/prescriptionDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void notShowPrescriptionDetails() throws Exception {
		this.mockMvc
				.perform(get("/doctor/patients/{patientId}/prescriptions/{prescriptionId}", 1, -1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("empty"))
				.andExpect(view().name("/doctor/prescriptions/prescriptionDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void deletePrescription() throws Exception {
		Map<String, Object> model = new HashMap<>();
		this.doctorPrescriptionController.initDelete(1, model);
		Mockito.verify(this.prescriptionService).deletePrescription(this.prescription1);
	}

	@WithMockUser(value = "spring")
	@Test
	void notDeletePrescription() throws Exception {
		Map<String, Object> model = new HashMap<>();
		this.doctorPrescriptionController.initDelete(-1, model);
		Mockito.verify(this.prescriptionService).deletePrescription(null);

	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void shouldInitCreatePrescriptionForm() throws Exception {
		this.mockMvc
				.perform(get("/doctor/patients/{patientId}/prescriptions/new",
						DoctorPrescriptionControllerTests.TEST_PATIENT_ID_1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("prescription"))
				.andExpect(view().name("/doctor/prescriptions/createPrescriptionForm"));
	}

	@Test
	@WithMockUser(username = "doctor1", roles = {"doctor"})
	void shouldProcessCreatePrescriptionForm() throws Exception {
		this.mockMvc
				.perform(post("/doctor/patients/{patientId}/prescriptions/new",
						DoctorPrescriptionControllerTests.TEST_PATIENT_ID_1).with(csrf())
								.param("dosage", "3").param("days", "3")
								.param("pharmaceuticalWarning", "None")
								.param("patientWarning", "None").param("medicine", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/doctor/patients/{patientId}/prescriptions"));
	}

}
