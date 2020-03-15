package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime			startTime;

	@Column(name = "end_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime			endTime;

	@Column(name = "anamnesis")
	private String					anamnesis;

	@Column(name = "remarks")
	private String					remarks;

	@ManyToOne(optional = true)
	@JoinColumn(name = "discharge_type_id")
	private DischargeType			dischargeType;

	@OneToMany
	@JoinColumn(name = "consultation_id")
	private Collection<Examination>	examinations;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "consultation_diagnoses", joinColumns = @JoinColumn(name = "consultation_id"), inverseJoinColumns = @JoinColumn(name = "diagnosis_id"))
	private Collection<Diagnosis>	diagnoses;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "consultation_constants", joinColumns = @JoinColumn(name = "consultation_id"), inverseJoinColumns = @JoinColumn(name = "constant_id"))
	private Collection<Constant>	constants;

	@OneToOne
	@JoinColumn(name = "appointment_id")
	private Appointment				appointment;

}