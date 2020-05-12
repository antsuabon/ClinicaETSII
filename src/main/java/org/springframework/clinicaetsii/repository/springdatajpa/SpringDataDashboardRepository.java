package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.NamedEntity;
import org.springframework.clinicaetsii.repository.DashboardRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataDashboardRepository
		extends CrudRepository<NamedEntity, Long>, DashboardRepository {


	// @Override
	// public Double getAverageNumberOfPrescriptionsByDoctor();
	//
	// @Override
	// public List<String> getMostFrequentDiagnosesLabels();
	//
	// @Override
	// public List<Long> getMostFrequentDiagnosesValues();
	//
	// @Override
	// public List<String> getNumberOfConsultationsByDischargeTypeLabels();
	//
	// @Override
	// public List<Long> getNumberOfConsultationsByDischargeTypeValues();
	//
	// @Override
	// public List<String> getMostFrequestMedicinesLabels();
	//
	// @Override
	// public List<Long> getMostFrequestMedicinesValues();
	//
	// @Override
	// public Double getAverageWaitingTime();
	//
	// @Override
	// @Query()
	// public Long getAverageDiagnosesPerConsultation();

	@Override
	@Query("select avg(datediff(year, patient.birthDate, CURRENT_TIMESTAMP)) from Patient patient")
	public Double getAverageAge();
}
