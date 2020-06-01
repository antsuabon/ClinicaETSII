package org.springframework.clinicaetsii.web.e2e.admin;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Every.everyItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AdminDashboardControllerE2ETests {

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	public void shouldGetDashboard() throws Exception {
		this.mockMvc.perform(get("/admin/dashboard")).andExpect(status().isOk())
				.andExpect(model().attributeExists("dashboard"))
				.andExpect(model().attribute("dashboard",
						hasProperty("averageNumberOfPrescriptionsByDoctor",
								allOf(notNullValue(), greaterThanOrEqualTo(0.)))))
				.andExpect(model().attribute("dashboard",
						hasProperty("mostFrequentDiagnosesLabels",
								everyItem(allOf(notNullValue(), not(emptyString()))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("mostFrequentDiagnosesValues",
								everyItem(allOf(notNullValue(), greaterThanOrEqualTo(0l))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("numberOfConsultationsByDischargeTypeLabels",
								everyItem(allOf(notNullValue(), not(emptyString()))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("numberOfConsultationsByDischargeTypeValues",
								everyItem(allOf(notNullValue(), greaterThanOrEqualTo(0l))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("mostFrequestMedicinesLabels",
								everyItem(allOf(notNullValue(), not(emptyString()))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("mostFrequestMedicinesValues",
								everyItem(allOf(notNullValue(), greaterThanOrEqualTo(0l))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("averageWaitingTime", notNullValue())))
				.andExpect(model().attribute("dashboard",
						hasProperty("ratioServicesPatientsNumServices",
								everyItem(allOf(notNullValue(), greaterThanOrEqualTo(0l))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("ratioServicesPatientsAvgPatients",
								everyItem(allOf(notNullValue(), greaterThanOrEqualTo(0.))))))
				.andExpect(model().attribute("dashboard",
						hasProperty("averageDiagnosesPerConsultation",
								allOf(notNullValue(), greaterThanOrEqualTo(0.)))))
				.andExpect(model().attribute("dashboard",
						hasProperty("averageAge", allOf(notNullValue(), greaterThanOrEqualTo(0.)))))
				.andExpect(view().name("/admin/dashboard/show"));
	}

	@WithMockUser(value = "spring")
	@Test
	public void shouldNotGetDashboard() throws Exception {
		this.mockMvc.perform(get("/admin/dashboard")).andExpect(status().is(403));
	}
}
