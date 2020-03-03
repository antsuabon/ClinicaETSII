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
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {

	@Column(name = "start_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	@NotNull
	private LocalDateTime startDate;

	@Column(name = "dosage")
	private float dosage;

	@Column(name = "days")
	private float days;

	@Column(name = "pharmaceutical_warning")
	private String pharmaceuticalWarning;

	@Column(name = "patient_warning")
	private String patientWarning;

	@ManyToOne(optional = false)
	@JoinColumn(name = "medicine_id")
	@NotNull
	private Medicine medicine;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patient_id")
	@NotNull
	private Patient patient;

	@ManyToOne(optional = false)
	@JoinColumn(name = "doctor_id")
	@NotNull
	private Doctor doctor;
	
	public float getNumDoses() {
		return 24*this.getDays()/this.getDosage();
	}

	public LocalDateTime getEndDate() {
		return this.getStartDate().plusDays(Float.valueOf(this.getDays()).longValue());
	}

}
