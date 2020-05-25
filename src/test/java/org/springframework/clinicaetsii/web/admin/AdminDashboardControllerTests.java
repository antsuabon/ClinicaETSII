package org.springframework.clinicaetsii.web.admin;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Every.everyItem;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.form.Dashboard;
import org.springframework.clinicaetsii.service.DashboardService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdminDashboardController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminDashboardControllerTests {

	@MockBean
	private DashboardService dashboardService;

	@Autowired
	private AdminDashboardController adminDashboardController;

	@Autowired
	private MockMvc mockMvc;

	private Dashboard dashboard;

	@BeforeEach
	public void setup() {

		this.dashboard = new Dashboard();
		this.dashboard.setAverageNumberOfPrescriptionsByDoctor(20.);
		this.dashboard.setMostFrequentDiagnosesLabels(
				Arrays.asList("Rotura de rodilla", "Gripe invernal", "Resfriado severo"));
		this.dashboard.setMostFrequentDiagnosesValues(Arrays.asList(5l, 4l, 3l));
		this.dashboard.setNumberOfConsultationsByDischargeTypeLabels(
				Arrays.asList("Hospital", "Revisión"));
		this.dashboard.setNumberOfConsultationsByDischargeTypeValues(Arrays.asList(3l, 2l));
		this.dashboard.setMostFrequestMedicinesLabels(Arrays.asList("Ibuprofeno", "Paracetamol"));
		this.dashboard.setMostFrequestMedicinesValues(Arrays.asList(5l, 4l));
		this.dashboard.setAverageWaitingTime(65.);
		this.dashboard.setRatioServicesPatientsNumServices(Arrays.asList(1l, 4l, 5l));
		this.dashboard.setRatioServicesPatientsAvgPatients(Arrays.asList(2., 3., 5.));
		this.dashboard.setAverageDiagnosesPerConsultation(1.2);
		this.dashboard.setAverageAge(45.75);

		given(this.dashboardService.getDashboard()).willReturn(this.dashboard);
	}

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

}
