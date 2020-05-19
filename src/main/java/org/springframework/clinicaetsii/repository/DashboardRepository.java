package org.springframework.clinicaetsii.repository;

import java.util.List;
import javax.persistence.Tuple;

public interface DashboardRepository {

	public Double getAverageNumberOfPrescriptionsByDoctor();

	public List<Tuple> getMostFrequentDiagnoses();

	public List<Tuple> getNumberOfConsultationsByDischargeType();

	public List<Tuple> getMostFrequestMedicines();

	public Double getAverageWaitingTime();

	public List<Tuple> getRatioServicesPatientsNumServices();

	public Double getAverageDiagnosesPerConsultation();

	public Double getAverageAge();

}
