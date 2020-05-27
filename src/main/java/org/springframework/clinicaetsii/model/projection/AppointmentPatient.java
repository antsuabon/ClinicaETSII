package org.springframework.clinicaetsii.model.projection;

import java.time.LocalDateTime;

public interface AppointmentPatient {

	Integer getPatientId();

	Integer getAppointmentId();

	String getName();

	String getSurname();

	LocalDateTime getStartTime();

	LocalDateTime getEndTime();
}
