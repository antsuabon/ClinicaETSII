package org.springframework.clinicaetsii.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrescriptionService {

  private PrescriptionRepository prescriptionRepository;

  @Autowired
  public PrescriptionService(final PrescriptionRepository prescriptionRepository) {
    this.prescriptionRepository = prescriptionRepository;
  }

  @Transactional(readOnly = true)
  public Collection<Prescription> findPrescriptionsByPatientUsername(final String username)
      throws DataAccessException {
    return this.prescriptionRepository.findPrescriptionsByPatientUsername(username);
  }

  @Transactional(readOnly = true)
  public Collection<Prescription> findPrescriptionsByPatientId(final int patientId)
      throws DataAccessException {
    return this.prescriptionRepository.findPrescriptionsByPatientIdOrdered(patientId);
  }

  @Transactional(readOnly = true)
  public Prescription findPrescriptionById(final int prescriptionId) throws DataAccessException {
    return this.prescriptionRepository.findById(prescriptionId);
  }

  @Transactional(readOnly = true)
  public Collection<Prescription> findAllPrescriptions() throws DataAccessException {
    return this.prescriptionRepository.findAll();
  }

  @Transactional(readOnly = true)
  public void savePrescription(final Prescription prescription) throws DataAccessException {
    this.prescriptionRepository.save(prescription);
  }

  @Transactional
  public void deletePrescription(final Prescription prescription) throws DataAccessException {
    this.prescriptionRepository.delete(prescription);
  }
  
  @Transactional(readOnly = true)
  public Collection<Prescription> findAllPrescriptionsByDoctor(final int doctorId) throws DataAccessException {
    return this.prescriptionRepository.findPrescriptionByDoctorId(doctorId);
  }

}
