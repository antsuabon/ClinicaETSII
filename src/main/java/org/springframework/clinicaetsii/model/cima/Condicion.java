package org.springframework.clinicaetsii.model.cima;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Condicion implements Serializable {

	private static final long serialVersionUID = 1L;

	private String seccion;

	private String texto;

	private Integer contiene;
}
