package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.repository.ConstantRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataConstantRepository extends ConstantRepository, CrudRepository<Constant, Integer> {

}
