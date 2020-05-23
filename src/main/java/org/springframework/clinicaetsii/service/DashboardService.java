package org.springframework.clinicaetsii.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.form.Dashboard;
import org.springframework.clinicaetsii.repository.DashboardRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ComponentScan("org.springframework.clinicaetsii.repository.custom")
public class DashboardService {

	private DashboardRepository dashboardRepository;

	@Autowired
	public DashboardService(final DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}

	@Transactional(readOnly = true)
	public Dashboard getDashboard() {
		Dashboard dashboard = new Dashboard();

		dashboard.setAverageNumberOfPrescriptionsByDoctor(
				this.dashboardRepository.getAverageNumberOfPrescriptionsByDoctor());

		List<Tuple> diagnosesByName = this.dashboardRepository.getMostFrequentDiagnoses();
		dashboard.setMostFrequentDiagnosesLabels(diagnosesByName.stream()
				.map(t -> String.valueOf(t.get(0))).collect(Collectors.toList()));
		dashboard.setMostFrequentDiagnosesValues(diagnosesByName.stream()
				.map(t -> Long.valueOf(String.valueOf(t.get(1)))).collect(Collectors.toList()));

		List<Tuple> dischargeTypesByName =
				this.dashboardRepository.getNumberOfConsultationsByDischargeType();
		dashboard.setNumberOfConsultationsByDischargeTypeLabels(dischargeTypesByName.stream()
				.map(t -> String.valueOf(t.get(0))).collect(Collectors.toList()));
		dashboard.setNumberOfConsultationsByDischargeTypeValues(dischargeTypesByName.stream()
				.map(t -> Long.valueOf(String.valueOf(t.get(1)))).collect(Collectors.toList()));

		List<Tuple> topMedicinesByName = this.dashboardRepository.getMostFrequestMedicines();
		dashboard.setMostFrequestMedicinesLabels(topMedicinesByName.stream()
				.map(t -> String.valueOf(t.get(0))).limit(5).collect(Collectors.toList()));
		dashboard.setMostFrequestMedicinesValues(
				topMedicinesByName.stream().map(t -> Long.valueOf(String.valueOf(t.get(1))))
						.limit(5).collect(Collectors.toList()));

		Map<Long, Double> ratioServicesPatients = this.dashboardRepository
				.getRatioServicesPatientsNumServices().stream()
				.collect(Collectors.groupingBy(t -> Long.valueOf(String.valueOf(t.get(0))),
						Collectors.averagingDouble(t -> Double.valueOf(String.valueOf(t.get(1))))));
		dashboard.setRatioServicesPatientsNumServices(
				new ArrayList<>(ratioServicesPatients.keySet()));
		dashboard.setRatioServicesPatientsAvgPatients(
				dashboard.getRatioServicesPatientsNumServices().stream()
						.map(n -> ratioServicesPatients.get(n)).collect(Collectors.toList()));

		dashboard.setAverageWaitingTime(this.dashboardRepository.getAverageWaitingTime());

		dashboard.setAverageDiagnosesPerConsultation(
				this.dashboardRepository.getAverageDiagnosesPerConsultation());

		dashboard.setAverageAge(this.dashboardRepository.getAverageAge());

		return dashboard;
	}


}
