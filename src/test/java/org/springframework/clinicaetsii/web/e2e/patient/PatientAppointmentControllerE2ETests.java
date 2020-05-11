package org.springframework.clinicaetsii.web.e2e.patient;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.clinicaetsii.repository.ConstantRepository;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.repository.DiagnosisRepository;
import org.springframework.clinicaetsii.repository.ExaminationRepository;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PatientAppointmentControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "patient1", authorities = {"patient"})
	@Test
	void testGenerateEmptyTable() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments/table"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/patient/appointments/timeTable"));
	}

	@WithMockUser(username = "patient1", authorities = {"patient"})
	@Test
	void testInitForm() throws Exception {
		String fecha = LocalDateTime.now().plusHours(8).format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/patient/appointments/new").param("fecha",
						fecha))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/appointments/requestAppointment"));
	}

	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void testProcessCreationForm() throws Exception {

		String startTime =
				LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);
		String endTime = LocalDateTime.of(2020, 3, 26, 9, 0).plusMinutes(7)
				.format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc
				.perform(post("/patient/appointments/save").with(csrf())
						.param("startTime", startTime).param("endTime", endTime))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(
						MockMvcResultMatchers.view().name("redirect:/patient/appointments/table"));

	}

	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void testNotProcessCreationForm() throws Exception {

		String startTime =
				LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/patient/appointments/save")
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime", startTime))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/patient/appointments/new"));

	}


	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldListAppointments() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointmentsDone"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointmentsDelete"))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/appointments/appointmentsList"));

	}

	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	void shouldListEmptyAppointments() throws Exception {

		clearAppointments();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
				.andExpect(MockMvcResultMatchers.model().attribute("appointmentsDone", true))
				.andExpect(MockMvcResultMatchers.model().attribute("appointmentsDelete", true))
				.andExpect(MockMvcResultMatchers.view()
						.name("/patient/appointments/appointmentsList"));

	}

	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotListAppointments() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
				.andExpect(status().is(403));

	}


	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void administrativeShouldNotListAppointments() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
				.andExpect(status().is(403));

	}



	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotListAppointments() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/appointments"))
				.andExpect(status().is(403));

	}



	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldDeleteAppointments() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",
						4))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view().name("redirect:/patient/appointments"));

	}


	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldNotDeleteAppointments() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",
						-1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}



	@Test
	@WithMockUser(username = "patient1", authorities = {"patient"})
	void shouldNotDeleteAppointmentsDone() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete",
						1))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("exception"));

	}


	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotDeleteAppointments() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete", 2))
				.andExpect(status().is(403));

	}


	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void administrativeShouldNotDeleteAppointments() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete", 2))
				.andExpect(status().is(403));

	}



	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotDeleteAppointments() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete", 2))
				.andExpect(status().is(403));

	}


	@Test
	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	void doctorShouldNotDeleteAppointmentsDone() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete", 1))
				.andExpect(status().is(403));

	}


	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void administrativeShouldNotDeleteAppointmentsDone() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete", 1))
				.andExpect(status().is(403));

	}



	@Test
	@WithMockUser(value = "spring")
	void anonymousShouldNotDeleteAppointmentsDone() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/patient/appointments/{appointmentId}/delete", 1))
				.andExpect(status().is(403));
	}


	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private ExaminationRepository examinationRepository;

	@Autowired
	private ConstantRepository constantRepository;

	@Autowired
	private ConsultationRepository consultationRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public void clearAppointments() {
		try {
			for (Consultation consultation : this.consultationRepository.findAll()) {
				consultation.setConstants(null);
				consultation.setExaminations(null);
				consultation.setDiagnoses(null);

				this.consultationRepository.save(consultation);
				this.consultationRepository.delete(consultation);

			}

			this.constantRepository.deleteAll();
			this.examinationRepository.deleteAll();
			this.diagnosisRepository.deleteAll();
			this.prescriptionRepository.deleteAll();
			this.appointmentRepository.deleteAll();



		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
