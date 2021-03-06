
package org.springframework.clinicaetsii.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "appointments", indexes = {@Index(columnList = "priority, start_time desc")})
public class Appointment extends BaseEntity {

	@Column(name = "start_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime startTime;

	@Column(name = "end_time")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime endTime;

	@Column(name = "priority")
	private boolean priority;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patient_id")

	private Patient patient;

}
