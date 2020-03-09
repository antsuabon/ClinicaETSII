package org.springframework.clinicaetsii.repository;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Prescription;

public interface PrescriptionRepository {
	
	Collection<Prescription> listPrescriptionsByPatient(int patientId);
	

}
