package org.springframework.clinicaetsii.model.cima;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrincipioActivo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String codigo;

	private String nombre;

	private String cantidad;

	private String unidad;

	private Integer orden;

}
