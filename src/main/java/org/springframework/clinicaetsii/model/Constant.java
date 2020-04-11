
package org.springframework.clinicaetsii.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "constants")
public class Constant extends BaseEntity {

	@Column(name = "value_constant")
	private float			value;

	@Valid
	@ManyToOne
	@JoinColumn(name = "constant_type_id")
	private ConstantType	constantType;

}