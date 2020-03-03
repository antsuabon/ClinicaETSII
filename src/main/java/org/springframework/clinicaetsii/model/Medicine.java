package org.springframework.clinicaetsii.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table( name = "medicines" )
public class Medicine extends BaseEntity{

	@Column(name = "generical_name")
	@NotBlank
	private String genericalName;

	@Column(name = "commercial_name")
	@NotBlank
	private String comemercialName;

	@Column(name = "quantity")
	private float quantity;

	@Column(name = "indications")
	private String indications;

	@Column(name = "contraindications")
	private String contraindications;

}
