package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.repository.DoctorRepository;

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
	public Doctor findDoctorByUsername(String username) throws DataAccessException {
		return this.doctorRepository.findDoctorByUsername(username);
	}
  
  public Collection<Integer> findAllDoctorsId() {
		return this.doctorRepository.findAllDoctorsId();
	}
	
	public Doctor findDoctorById(int id) {
		return this.doctorRepository.findDoctorById(id);
	}
	
	public Collection<Doctor> findAllDoctors() {
		return this.doctorRepository.findAllDoctors();
	}

}
