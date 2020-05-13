package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Administrative;
import org.springframework.clinicaetsii.repository.AdministrativeRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AdministrativeService {
	
	private AdministrativeRepository administrativeRepository;
	
	@Autowired
	public AdministrativeService (AdministrativeRepository administrativeRepository) {
		this.administrativeRepository = administrativeRepository;
	}
	
	public Collection<Administrative> findAllAdministratives() throws DataAccessException {
		return this.administrativeRepository.findAll();
	}
	
	public Administrative findAdministrativeById(int id) throws DataAccessException {
		return this.administrativeRepository.findById(id);
	}
	
	public void delete(Administrative administrative) throws DataAccessException {
		this.administrativeRepository.delete(administrative);
	}

}
