package org.springframework.clinicaetsii.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table( name = "medicines" )
public class Medicine extends BaseEntity{

	@Column(name = "generical_name")
	private String genericalName;

	@Column(name = "commercial_name")
	private String comemercialName;

	@Column(name = "quantity")
	private Double quantity;

}
