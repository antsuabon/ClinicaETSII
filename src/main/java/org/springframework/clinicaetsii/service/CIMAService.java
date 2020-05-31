package org.springframework.clinicaetsii.service;

import org.springframework.clinicaetsii.model.cima.ConsultaFiltro;
import org.springframework.clinicaetsii.model.cima.ConsultaMedicamento;
import org.springframework.clinicaetsii.model.cima.Medicamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CIMAService {

	private RestTemplate restTemplate;

	private static final String BASE_URL = "https://cima.aemps.es/cima/rest";
	private static final String MEDICAMENTO_URL = CIMAService.BASE_URL + "/medicamento";
	private static final String MEDICAMENTOS_URL = CIMAService.BASE_URL + "/medicamentos";
	private static final String MAESTRAS_URL = CIMAService.BASE_URL + "/maestras";

	private static final String MAESTRAS_URL_WITH_QUERIES =
			CIMAService.MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}";

	public CIMAService() {
		this.restTemplate = new RestTemplate();
	}

	@Transactional(readOnly = true)
	public Medicamento findMedicamentoByNregistro(final int nregistro) throws RestClientException {
		return this.restTemplate.getForObject(
				CIMAService.MEDICAMENTO_URL + "?nregistro={nregistro}", Medicamento.class,
				nregistro);
	}

	@Transactional(readOnly = true)
	public ConsultaMedicamento findMedicamentosByAttributes(final String nombre,
			final String pactivos,
			final String labtitular) throws RestClientException {

		return this.restTemplate.getForObject(
				MEDICAMENTOS_URL + "?nombre={nombre}&practiv1={practiv1}&laboratorio={laboratorio}",
				ConsultaMedicamento.class, nombre == null ? "" : nombre,
				pactivos == null ? "" : pactivos, labtitular == null ? "" : labtitular);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findViasDeAdministracionByNmobre(final String nombre,
			final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(MAESTRAS_URL_WITH_QUERIES, ConsultaFiltro.class, 4,
				nombre, pagina);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findLaboratoriosByNombre(final String nombre,
			final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(MAESTRAS_URL_WITH_QUERIES, ConsultaFiltro.class, 6,
				nombre, pagina);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findFormasFarmaceuticasByNombre(final String nombre,
			final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(MAESTRAS_URL_WITH_QUERIES, ConsultaFiltro.class, 3,
				nombre, pagina);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findPrincipiosActivosByNombre(final String nombre,
			final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(MAESTRAS_URL_WITH_QUERIES, ConsultaFiltro.class, 1,
				nombre, pagina);
	}

}
