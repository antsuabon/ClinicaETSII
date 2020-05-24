package org.springframework.clinicaetsii.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.form.Dashboard;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.repository.DiagnosisRepository;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.clinicaetsii.repository.PatientRepository;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void shouldGetEmptyDashboard() {
		clearDatabase();

		Dashboard dashboard = this.dashboardService.getDashboard();

		assertThat(dashboard).isNotNull();
		assertThat(dashboard.getAverageNumberOfPrescriptionsByDoctor()).isNotNull().isEqualTo(0.);
		assertThat(dashboard.getMostFrequentDiagnosesLabels()).isNotNull().isEmpty();
		assertThat(dashboard.getMostFrequentDiagnosesValues()).isNotNull().isEmpty();
		assertThat(dashboard.getNumberOfConsultationsByDischargeTypeLabels()).isNotNull().isEmpty();
		assertThat(dashboard.getNumberOfConsultationsByDischargeTypeValues()).isNotNull().isEmpty();
		assertThat(dashboard.getMostFrequestMedicinesLabels()).isNotNull().isEmpty();
		assertThat(dashboard.getMostFrequestMedicinesValues()).isNotNull().isEmpty();
		assertThat(dashboard.getAverageWaitingTime()).isNotNull().isEqualTo(0.);
		assertThat(dashboard.getRatioServicesPatientsNumServices()).isNotNull().isEmpty();
		assertThat(dashboard.getRatioServicesPatientsAvgPatients()).isNotNull().isEmpty();
		assertThat(dashboard.getAverageDiagnosesPerConsultation()).isNotNull().isEqualTo(0.);
		assertThat(dashboard.getAverageAge()).isNotNull().isEqualTo(0.);
	}



	@Autowired
	private ConsultationRepository consultationRepository;

	@Autowired
	private DiagnosisRepository diagnosisRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	public void clearDatabase() {
		try {

			this.prescriptionRepository.deleteAll();
			this.consultationRepository.deleteAll();
			this.diagnosisRepository.deleteAll();
			this.appointmentRepository.deleteAll();
			this.patientRepository.deleteAll();
			this.doctorRepository.deleteAll();

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
