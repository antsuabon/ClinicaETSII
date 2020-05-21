package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.clinicaetsii.model.NamedEntity;
import org.springframework.clinicaetsii.repository.DashboardRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataDashboardRepository
		extends CrudRepository<NamedEntity, Long>, DashboardRepository {


	@Override
	@Query("select avg(select count(prescription) from Prescription prescription where prescription.doctor.id = doctor.id) from Doctor doctor")
	public Double getAverageNumberOfPrescriptionsByDoctor();

	@Override
	@Query("select diagnosis.name, count(diagnosis.name) from Diagnosis diagnosis group by diagnosis.name order by count(diagnosis.name) desc")
	public List<Tuple> getMostFrequentDiagnoses();

	@Override
	@Query("select consultation.dischargeType.name, count(consultation) from Consultation consultation group by consultation.dischargeType.name order by count(consultation) desc")
	public List<Tuple> getNumberOfConsultationsByDischargeType();

	@Override
	@Query("select prescription.medicine.genericalName, count(prescription) from Prescription prescription group by prescription.medicine.genericalName order by count(prescription) desc")
	public List<Tuple> getMostFrequestMedicines();

	@Override
	@Query("select avg(timediff(consultation.startTime, consultation.appointment.startTime)) from Consultation consultation")
	public Double getAverageWaitingTime();

	@Override
	@Query("select doctor.services.size as numServices, (select count(patient) from Patient patient where patient.generalPractitioner.id = doctor.id) from Doctor doctor group by doctor.id")
	public List<Tuple> getRatioServicesPatientsNumServices();

	@Override
	@Query("select avg(select count(diagnosis) from Diagnosis diagnosis where diagnosis.id = consultation.id) from Consultation consultation")
	public Double getAverageDiagnosesPerConsultation();

	@Override
	@Query("select avg(datediff(CURRENT_TIMESTAMP,patient.birthDate)/365) from Patient patient")
	public Double getAverageAge();
}
