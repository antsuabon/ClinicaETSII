
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.repository.ConsultationRepository;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.clinicaetsii.service.exceptions.DeleteDoctorException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;

	private ConsultationRepository consultationRepository;
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	public DoctorService(final DoctorRepository doctorRepository,
			final ConsultationRepository consultationRepository,
			final PrescriptionRepository prescriptionRepository) {
		this.doctorRepository = doctorRepository;

		this.consultationRepository = consultationRepository;
		this.prescriptionRepository = prescriptionRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Doctor> findAllDoctors() throws DataAccessException {
		return this.doctorRepository.findAll();
	}


	@Transactional(readOnly = true)
	@Cacheable("doctorsWithServices")
	public Collection<Doctor> findAllDoctorsWithServices() throws DataAccessException {
		return this.doctorRepository.findDoctorsWithServices();
	}

	@Transactional(readOnly = true)
	public Collection<Doctor> findDoctorsSortedByNumOfServices() throws DataAccessException {
		return this.doctorRepository.findDoctorsSortedByNumOfServices();
	}

	@Transactional(readOnly = true)
	public Doctor findDoctorByUsername(final String username) throws DataAccessException {
		return this.doctorRepository.findDoctorByUsername(username);
	}


	@Transactional(readOnly = true)
	public Doctor findDoctorById(final int id) {
		return this.doctorRepository.findDoctorById(id);
	}

	private String getUsernameCurrentDoctor() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		return user.getUsername();
	}

	@PreAuthorize("hasAuthority('doctor')")
	@Transactional(readOnly = true)
	public Doctor findCurrentDoctor() throws DataAccessException {
		return this.doctorRepository.findDoctorByUsername(this.getUsernameCurrentDoctor());
	}

	@Transactional
	public void save(
			final Doctor doctor) throws DataAccessException, DataIntegrityViolationException {
		this.doctorRepository.save(doctor);
	}

	@Transactional(readOnly = true)
	public Collection<org.springframework.clinicaetsii.model.Service> findAllServices() {
		return this.doctorRepository.findAllServices();
	}

	@Transactional(readOnly = true)
	public Boolean isErasable(final Doctor doctor) throws DataAccessException {
		return doctor != null
				&& this.consultationRepository.findConsultationsByDoctorId(doctor.getId()).isEmpty()
				&& this.prescriptionRepository.findPrescriptionByDoctorId(doctor.getId()).isEmpty();
	}

	@Transactional
	public void delete(
			final Doctor doctor) throws DataAccessException, DataIntegrityViolationException, DeleteDoctorException {

		if (this.isErasable(doctor)) {
			this.doctorRepository.delete(doctor);
		} else {
			throw new DeleteDoctorException();
		}
	}

}
