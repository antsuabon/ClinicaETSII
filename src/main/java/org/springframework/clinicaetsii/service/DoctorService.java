package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

	private DoctorRepository doctorRepository;


	@Autowired
	public DoctorService(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
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
