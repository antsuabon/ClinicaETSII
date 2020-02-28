package org.springframework.clinicaetsii.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {

	private LocalDate startDate;

	private double dosage;
	private double days;

	private String pharmaceuticalWarning;
	private String patientWarning;

	// TODO Propiedad derivada numDoses
	public int getNumDoses() {
		return 0;
	}

	// TODO Propiedad derivada endDate
	public LocalDate getEndDate() {
		return null;
	}

}
