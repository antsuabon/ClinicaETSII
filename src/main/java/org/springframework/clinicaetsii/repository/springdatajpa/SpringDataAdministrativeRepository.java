package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Administrative;
import org.springframework.clinicaetsii.repository.AdministrativeRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataAdministrativeRepository extends AdministrativeRepository, CrudRepository<Administrative, Integer>{

}