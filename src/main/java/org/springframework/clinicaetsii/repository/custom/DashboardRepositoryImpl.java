package org.springframework.clinicaetsii.repository.custom;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import org.springframework.clinicaetsii.repository.DashboardRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Double getAverageNumberOfPrescriptionsByDoctor() {
		TypedQuery<Double> query = this.entityManager.createQuery(
				"select avg(select count(prescription) from Prescription prescription where prescription.doctor.id = doctor.id) from Doctor doctor",
				Double.class);

		return query.getSingleResult();
	}

	@Override
	public List<Tuple> getMostFrequentDiagnoses() {
		TypedQuery<Tuple> query = this.entityManager.createQuery(
				"select diagnosis.name, count(diagnosis) from Consultation consultation left join consultation.diagnoses diagnosis where diagnosis.name != null group by diagnosis.name order by count(diagnosis) desc",
				Tuple.class);

		return query.getResultList();
	}

	@Override
	public List<Tuple> getNumberOfConsultationsByDischargeType() {
		TypedQuery<Tuple> query = this.entityManager.createQuery(
				"select consultation.dischargeType.name, count(consultation) from Consultation consultation group by consultation.dischargeType.name order by count(consultation) desc",
				Tuple.class);

		return query.getResultList();
	}

	@Override
	public List<Tuple> getMostFrequestMedicines() {
		TypedQuery<Tuple> query = this.entityManager.createQuery(
				"select prescription.medicine.genericalName, count(prescription) from Prescription prescription group by prescription.medicine.genericalName order by count(prescription) desc",
				Tuple.class);

		return query.getResultList();
	}

	@Override
	public Double getAverageWaitingTime() {
		TypedQuery<Double> query = this.entityManager.createQuery(
				"select avg(extract(SECOND FROM (consultation.startTime)) - extract(SECOND FROM consultation.appointment.startTime)) from Consultation consultation where consultation.dischargeType != null",
				Double.class);

		return query.getSingleResult();
	}

	@Override
	public List<Tuple> getRatioServicesPatientsNumServices() {
		TypedQuery<Tuple> query = this.entityManager.createQuery(
				"select doctor.services.size as numServices, (select count(patient) from Patient patient where patient.generalPractitioner.id = doctor.id) from Doctor doctor group by doctor.id",
				Tuple.class);

		return query.getResultList();
	}

	@Override
	public Double getAverageDiagnosesPerConsultation() {
		TypedQuery<Double> query = this.entityManager.createQuery(
				"select avg(select count(diagnosis) from Diagnosis diagnosis where diagnosis.id = consultation.id) from Consultation consultation",
				Double.class);

		return query.getSingleResult();
	}

	@Override
	public Double getAverageAge() {
		TypedQuery<Double> query = this.entityManager.createQuery(
				"select avg(datediff(CURRENT_TIMESTAMP, patient.birthDate) / 365.) from Patient patient",
				Double.class);

		return query.getSingleResult();
	}
}
