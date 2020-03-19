package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.repository.ConstantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstantService {

	private ConstantRepository constantRepository;

	@Autowired
	public ConstantService(final ConstantRepository constantRepository) {
		this.constantRepository = constantRepository;
	}

	@Transactional(readOnly = true)
	public Constant findConstantById(final int constantId) {
		return this.constantRepository.findById(constantId);
	}

	@Transactional
	public void saveConstant(final Constant constant) {
		this.constantRepository.save(constant);
	}

	@Transactional
	public void deleteConstant(final Constant constant) {
		this.constantRepository.delete(constant);
	}

	@Transactional(readOnly = true)
	public Collection<ConstantType> findAllConstantTypes() {
		return this.constantRepository.findAllConstantTypes();
	}

}
