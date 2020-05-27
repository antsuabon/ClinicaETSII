package org.springframework.clinicaetsii.model.projection;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AppointmentPatientImpl implements AppointmentPatient {

	private Integer patientId;
	private Integer appointmentId;
	private String name;
	private String surname;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
