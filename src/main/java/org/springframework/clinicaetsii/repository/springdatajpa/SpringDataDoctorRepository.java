/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.clinicaetsii.repository.springdatajpa;

import java.util.Collection;

import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataDoctorRepository extends DoctorRepository, CrudRepository<Doctor, Integer> {

	@Override
	@Query("select doctor from Doctor doctor order by doctor.services.size desc")
	Collection<Doctor> findDoctorsSortedByNumOfServices() throws DataAccessException;

	@Override
	@Query("select d from Doctor d where d.username =:username")
	Doctor findDoctorByUsername(@Param("username") String username);

	@Override
	@Query("SELECT d from Doctor d where d.id =:id")
	Doctor findDoctorById(@Param("id") int id);

	@Override
	@Query("SELECT DISTINCT patient.generalPractitioner FROM Patient patient WHERE patient.id =:id")
	Doctor findDoctorByPatientId(@Param("id") int id);

	@Override
	@Query("SELECT service FROM Service service")
	Collection<Service> findAllServices();
	
	@Override
	@Query("SELECT doctor from Doctor doctor left join fetch doctor.services services")
	Collection<Doctor> findAllDoctorsAndServices();

	@Override
	@Query("SELECT distinct doctor,doctor.services.size AS size from Doctor doctor left join fetch doctor.services services order by size desc ")
	Collection<Doctor> findDoctorsWithServices() throws DataAccessException;

}
