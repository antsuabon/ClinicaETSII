package org.springframework.clinicaetsii.model.form;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double averageNumberOfPrescriptionsByDoctor;

	private List<String> mostFrequentDiagnosesLabels;

	private List<Long> mostFrequentDiagnosesValues;

	private List<String> numberOfConsultationsByDischargeTypeLabels;

	private List<Long> numberOfConsultationsByDischargeTypeValues;

	private List<String> mostFrequestMedicinesLabels;

	private List<Long> mostFrequestMedicinesValues;

	private Double averageWaitingTime;

	private List<Long> ratioServicesPatientsNumServices;

	private List<Double> ratioServicesPatientsAvgPatients;

	private Double averageDiagnosesPerConsultation;

	private Double averageAge;

}
