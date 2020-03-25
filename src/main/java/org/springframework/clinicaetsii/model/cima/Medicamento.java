package org.springframework.clinicaetsii.model.cima;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medicamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nregistro;

	private String nombre;

	private String pactivos;

	private String labtitular;

	private String cpresc;

	private List<PrincipioActivo> principiosActivos;

	private List<Excipiente> excipientes;

	private String dosis;

}
