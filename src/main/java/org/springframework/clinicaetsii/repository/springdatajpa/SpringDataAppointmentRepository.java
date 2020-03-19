package org.springframework.clinicaetsii.repository.springdatajpa;

import org.springframework.clinicaetsii.model.Appointment;
import org.springframework.clinicaetsii.repository.AppointmentRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataAppointmentRepository extends AppointmentRepository, CrudRepository<Appointment, Integer>{

}
