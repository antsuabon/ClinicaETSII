package org.springframework.clinicaetsii.repository;

import org.springframework.clinicaetsii.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends  CrudRepository<User, Integer>{

}
