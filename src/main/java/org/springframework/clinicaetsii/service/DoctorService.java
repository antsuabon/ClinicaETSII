
package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;

	@Autowired
	public DoctorService(final DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Doctor> findAllDoctors() throws DataAccessException {
		return this.doctorRepository.findAll();
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
	public Collection<Integer> findAllDoctorsId() {
		return this.doctorRepository.findAllDoctorsId();
	}

	@Transactional(readOnly = true)
	public Doctor findDoctorById(final int id) {
		return this.doctorRepository.findDoctorById(id);
	}

	@PreAuthorize("hasAuthority('doctor')")
	@Transactional(readOnly = true)
	public Doctor findCurrentDoctor() throws DataAccessException {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		String username = user.getUsername();

		System.out.println(username);

		return this.doctorRepository.findDoctorByUsername(username);
	}

	@Transactional
	public void save(final Doctor doctor) throws DataAccessException {
		this.doctorRepository.save(doctor);
	}

	@Transactional(readOnly = true)
	public Collection<org.springframework.clinicaetsii.model.Service> findAllServices() {
		return this.doctorRepository.findAllServices();
	}

}
