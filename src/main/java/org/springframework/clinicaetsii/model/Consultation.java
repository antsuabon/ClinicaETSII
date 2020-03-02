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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table( name = "medical_consultations" )
public class Consultation extends BaseEntity {

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime startTime;

	@Column(name = "end_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime endTime;

	@Column(name = "anamnesis")
	private String anamnesis;

	@Column(name = "remarks")
	private String remarks;

	@ManyToOne(optional = true)
	@JoinColumn(name = "discharge_type_id")
	private DischargeType dischargeType;

	@OneToMany
	@JoinColumn(name = "examination_id")
	@NotEmpty
	private Collection<Examination> examinations;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "consultation_diagnoses", joinColumns = @JoinColumn(name = "consultation_id"), inverseJoinColumns = @JoinColumn(name = "diagnosis_id"))
	@NotNull
	private Collection<Diagnosis> diagnoses;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "consultation_constants", joinColumns = @JoinColumn(name = "consultation_id"), inverseJoinColumns = @JoinColumn(name = "constant_id"))
	@NotNull
	private Collection<Constant> constants;




}
