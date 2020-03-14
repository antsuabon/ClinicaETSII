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

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.clinicaetsii.repository.DoctorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA specialization of the {@link DoctorRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringAppointmentRepository extends AppointmentRepository, Repository<Appointment, Integer> {

	@Override
	@Query("SELECT appointment.startTime FROM Appointment appointment WHERE appointment.patient.generalPractitioner.id =:id")
	Collection<LocalDateTime> findAppointmentByDoctors(@Param("id") int id);
}
