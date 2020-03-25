package org.springframework.clinicaetsii.service;

import org.springframework.clinicaetsii.model.cima.ConsultaFiltro;
import org.springframework.clinicaetsii.model.cima.ConsultaMedicamento;
import org.springframework.clinicaetsii.model.cima.Medicamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CIMAService {

	private RestTemplate restTemplate;

	private static final String BASE_URL = "https://cima.aemps.es/cima/rest";
	private static final String MEDICAMENTO_URL = CIMAService.BASE_URL + "/medicamento";
	private static final String MEDICAMENTOS_URL = CIMAService.BASE_URL + "/medicamentos";
	private static final String MAESTRAS_URL = CIMAService.BASE_URL + "/maestras";

	public CIMAService() {
		this.restTemplate = new RestTemplate();
	}

	@Transactional(readOnly = true)
	public Medicamento findMedicamentoByNregistro(final String nregistro) throws RestClientException {
		return this.restTemplate.getForObject(CIMAService.MEDICAMENTO_URL + "?nregistro={nregistro}", Medicamento.class, nregistro);
	}

	@Transactional(readOnly = true)
	public ConsultaMedicamento findMedicamentosByAttributes(final String nombre, final String pactivos, final String labtitular) throws RestClientException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CIMAService.MEDICAMENTOS_URL);

		if (nombre != null && !StringUtils.isEmpty(nombre)) {
			builder.queryParam("nombre", nombre);
		}

		if (pactivos != null && !StringUtils.isEmpty(pactivos)) {
			builder.queryParam("pactivos", pactivos);
		}

		if (labtitular != null && !StringUtils.isEmpty(labtitular)) {
			builder.queryParam("labtitular", labtitular);
		}

		builder.queryParam("tamanioPagina", 10);

		return this.restTemplate.getForObject(builder.toUriString(), ConsultaMedicamento.class);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findViasDeAdministracionByNmobre(final String nombre, final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(CIMAService.MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}", ConsultaFiltro.class, 4, nombre, pagina);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findLaboratoriosByNombre(final String nombre, final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(CIMAService.MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}", ConsultaFiltro.class, 6, nombre, pagina);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findFormasFarmaceuticasByNombre(final String nombre, final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(CIMAService.MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}", ConsultaFiltro.class, 3, nombre, pagina);
	}

	@Transactional(readOnly = true)
	public ConsultaFiltro findPrincipiosActivosByNombre(final String nombre, final Integer pagina) throws RestClientException {
		return this.restTemplate.getForObject(CIMAService.MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}", ConsultaFiltro.class, 1, nombre, pagina);
	}

	public static void main(final String[] args) {
		CIMAService cima = new CIMAService();
//		System.out.println(cima.findMedicamentoByNregistro("65747"));
//		System.out.println(cima.findMedicamentosByNombre("ibuprofeno"));ACETILSALICILICO
		System.out.println(cima.findMedicamentosByAttributes("ibuprofeno", "ibuprofeno", null));
//		System.out.println(cima.findFormasFarmaceuticas());
//		System.out.println(cm.getResultados().size());
	}
}
