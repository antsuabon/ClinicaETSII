package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name ="appointments")
public class Appointment extends BaseEntity {

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	// TODO: Restricción de fecha en el futuro
	private LocalDateTime startTime;

	@Column(name = "end_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	// TODO: Restricción de fecha en el futuro
	private LocalDateTime endTime;

	@Column(name = "priority")
	private boolean priotity;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patient_id")
	@NotNull
	private Patient patient;

}
