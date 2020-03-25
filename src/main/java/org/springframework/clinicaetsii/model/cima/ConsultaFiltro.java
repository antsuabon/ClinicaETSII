package org.springframework.clinicaetsii.model.cima;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaFiltro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer totalFilas;

	private Integer pagina;

	private Integer tamanioPagina;

	private List<Filtro> resultados;
}
