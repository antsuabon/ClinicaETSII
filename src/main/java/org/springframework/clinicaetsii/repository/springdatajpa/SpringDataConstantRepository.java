package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.repository.ConstantRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataConstantRepository extends ConstantRepository, CrudRepository<Constant, Integer> {

	@Override
	@Query("SELECT constantType FROM ConstantType constantType")
	Collection<ConstantType> findAllConstantTypes();

}
