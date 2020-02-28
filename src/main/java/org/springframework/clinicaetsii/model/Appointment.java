package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name ="appointments")
public class Appointment extends BaseEntity {

	@Column(name = "start_time")
	private LocalDateTime startTime;

	@Column(name = "end_time")
	private LocalDateTime endTime;

	@Column(name = "priority")
	private boolean priotity;




}
