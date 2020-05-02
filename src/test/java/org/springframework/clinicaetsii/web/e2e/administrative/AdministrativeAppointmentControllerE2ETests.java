
package org.springframework.clinicaetsii.web.e2e.administrative;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AdministrativeAppointmentControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void testGenerateTable() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/administrative/patients/{patientId}/appointments/table", 4))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("hours"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/timeTable"));
	}

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void testGenerateEmptyTable() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/administrative/patients/{patientId}/appointments/table", 4))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/administrative/timeTable"));
	}

	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	@Test
	void testInitForm() throws Exception {
		String fecha = LocalDateTime.now().plusHours(8).format(DateTimeFormatter.ISO_DATE_TIME);
		this.mockMvc
				.perform(MockMvcRequestBuilders
						.get("/administrative/patients/{patientId}/appointments/new", 4)
						.param("fecha", fecha))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("appointment"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patientId"))
				.andExpect(MockMvcResultMatchers.view().name("/administrative/requestAppointment"));
	}

	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void testProcessCreationForm() throws Exception {
		String startTime =
				LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);
		String endTime = LocalDateTime.of(2020, 3, 26, 9, 0).plusMinutes(7)
				.format(DateTimeFormatter.ISO_DATE_TIME);

		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/administrative/patients/{patientId}/appointments/save", 4)
						.with(SecurityMockMvcRequestPostProcessors.csrf())
						.param("startTime", startTime).param("endTime", endTime))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.view()
						.name("redirect:/administrative/patients/{patientId}/appointments/table"));

	}

	@Test
	@WithMockUser(username = "administrative1", authorities = {"administrative"})
	void testProcessNotCreationForm() throws Exception {
		String startTime =
				LocalDateTime.of(2020, 3, 26, 9, 0).format(DateTimeFormatter.ISO_DATE_TIME);

		this.mockMvc
				.perform(post("/administrative/patients/{patientId}/appointments/save", 4)
						.with(csrf()).param("startTime", startTime))
				.andDo(print()).andExpect(status().is3xxRedirection()).andExpect(view()
						.name("redirect:/administrative/patients/{patientId}/appointments/new"));

	}

}
