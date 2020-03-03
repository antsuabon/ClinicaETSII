package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "examinations")
public class Examination extends BaseEntity {

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime startTime;

	@Column(name = "description")
	@NotBlank
	private String description;

}
