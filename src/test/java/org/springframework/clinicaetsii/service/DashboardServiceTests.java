package org.springframework.clinicaetsii.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.form.Dashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest(includeFilters = {@ComponentScan.Filter(Service.class),
		@ComponentScan.Filter(Repository.class)})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class DashboardServiceTests {

	@Autowired
	private DashboardService dashboardService;

	@Test
	public void shouldGetDashboard() {
		Dashboard dashboard = this.dashboardService.getDashboard();

		assertThat(dashboard).isNotNull();
		assertThat(dashboard.getAverageNumberOfPrescriptionsByDoctor()).isNotNull()
				.isGreaterThanOrEqualTo(0.);
		assertThat(dashboard.getMostFrequentDiagnosesLabels()).isNotNull().allSatisfy(label -> {
			assertThat(label).isNotEmpty();
		});
		assertThat(dashboard.getMostFrequentDiagnosesValues()).isNotNull().allSatisfy(count -> {
			assertThat(count).isNotNull().isGreaterThanOrEqualTo(0l);
		});
		assertThat(dashboard.getNumberOfConsultationsByDischargeTypeLabels()).isNotNull()
				.allSatisfy(label -> {
					assertThat(label).isNotEmpty();
				});
		assertThat(dashboard.getNumberOfConsultationsByDischargeTypeValues()).isNotNull()
				.allSatisfy(count -> {
					assertThat(count).isNotNull().isGreaterThanOrEqualTo(0l);
				});
		assertThat(dashboard.getMostFrequestMedicinesLabels()).isNotNull().allSatisfy(label -> {
			assertThat(label).isNotEmpty();
		});
		assertThat(dashboard.getMostFrequestMedicinesValues()).isNotNull().allSatisfy(count -> {
			assertThat(count).isNotNull().isGreaterThanOrEqualTo(0l);
		});
		assertThat(dashboard.getAverageWaitingTime()).isNotNull();
		assertThat(dashboard.getRatioServicesPatientsNumServices()).isNotNull()
				.allSatisfy(count -> {
					assertThat(count).isNotNull().isGreaterThanOrEqualTo(0l);
				});
		assertThat(dashboard.getRatioServicesPatientsAvgPatients()).isNotNull().allSatisfy(avg -> {
			assertThat(avg).isNotNull().isGreaterThanOrEqualTo(0.);
		});
		assertThat(dashboard.getAverageDiagnosesPerConsultation()).isNotNull()
				.isGreaterThanOrEqualTo(0.);
		assertThat(dashboard.getAverageAge()).isNotNull().isGreaterThanOrEqualTo(0.);
	}


}
