package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "medical_consultations" )
public class Consultation extends BaseEntity {

	@Column(name = "start_time")
	private LocalDateTime startTime;

	@Column(name = "end_time")
	private LocalDateTime endTime;

	@Column(name = "anamnesis")
	private String anamnesis;




}
