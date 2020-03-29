package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.User;
import org.springframework.clinicaetsii.repository.UserRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataUserRepository extends UserRepository, CrudRepository<User, Integer> {
	
	@Override
	@Query("SELECT u FROM User u WHERE u.username =:username")
	User findUserByUsername(@Param("username") String username);

}
