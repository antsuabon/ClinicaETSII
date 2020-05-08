package org.springframework.clinicaetsii.integration;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import lombok.extern.java.Log;

@Log
public class CimaAPITest {

	private static final String BASE_URL = "https://cima.aemps.es/cima/rest";
	private static final String MEDICAMENTO_URL = BASE_URL + "/medicamento";
	private static final String MEDICAMENTOS_URL = BASE_URL + "/medicamentos";
	private static final String MAESTRAS_URL = BASE_URL + "/maestras";


	@ParameterizedTest
	@CsvSource({
			"51347, ASPIRINA C 400 mg/240 mg COMPRIMIDOS EFERVESCENTES, 'ACETILSALICILICO ACIDO, ASCORBICO ACIDO', 'Bayer Hispania, S.L.', Sin Receta, COMPRIMIDO EFERVESCENTE, 400/240 mg/mg"})
	public void shouldFindMedicamento(final String nregistro,
			final String nombre,
			final String pactivos,
			final String labtitular,
			final String cpresc,
			final String formaFarmaceutica) {
		when().get(MEDICAMENTO_URL + "?nregistro={nregistro}", nregistro).then().statusCode(200)
				.assertThat().body("nregistro", is(nregistro)).body("nombre", is(nombre))
				.body("pactivos", is(pactivos)).body("labtitular", is(labtitular))
				.body("formaFarmaceutica.nombre", is(formaFarmaceutica)).and()
				.time(lessThan(20L), TimeUnit.SECONDS);
	}


	@ParameterizedTest
	@CsvSource({"1, acetil, 1", "1, ibuprofeno, 1", "6, cinfa, 1", "6, bayer, 1"})
	public void shouldFindMaestra(final String maestra, final String nombre, final String pagina) {
		when().get(MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}", maestra,
				nombre, pagina).then().statusCode(200).assertThat().body("resultados", not(empty()))
				.body("resultados.nombre", everyItem(containsStringIgnoringCase(nombre))).and()
				.time(lessThan(20L), TimeUnit.SECONDS);
	}

	@ParameterizedTest
	@CsvSource({"1, no existente, 1", "3, no existente, 1", "4, no existente, 1",
			"6, no existente, 1"})
	public void shouldNotFindMaestra(final String maestra,
			final String nombre,
			final String pagina) {
		when().get(MAESTRAS_URL + "?maestra={maestra}&nombre={nombre}&pagina={pagina}", maestra,
				nombre, pagina).then().statusCode(200).assertThat().body("resultados", empty())
				.and().time(lessThan(20L), TimeUnit.SECONDS);
	}

	@ParameterizedTest
	@CsvSource({"aspirina, ASCORBICO ACIDO, 'BAYER HISPANIA, S.L.'"})
	public void shouldFindMedicamentos(final String nombre,
			final String practiv,
			final String laboratorio) {
		when().get(
				MEDICAMENTOS_URL + "?nombre={nombre}&practiv1={practiv1}&laboratorio={laboratorio}",
				nombre, practiv, laboratorio).then().statusCode(200).assertThat()
				.body("pagina", is(1)).body("tamanioPagina", is(25))
				.body("resultados", not(empty()))
				.body("resultados.nombre", everyItem(containsStringIgnoringCase(nombre)))
				.body("resultados.labtitular", everyItem(containsStringIgnoringCase(laboratorio)))
				.and().time(lessThan(20L), TimeUnit.SECONDS);
	}

	@ParameterizedTest
	@CsvSource({"no existente, no existente, no existente"})
	public void shouldNotFindMedicamentos(final String nombre,
			final String practiv,
			final String laboratorio) {
		when().get(
				MEDICAMENTOS_URL + "?nombre={nombre}&practiv1={practiv1}&laboratorio={laboratorio}",
				nombre, practiv, laboratorio).then().statusCode(200).assertThat()
				.body("pagina", is(1)).body("tamanioPagina", is(25)).body("resultados", empty())
				.and().time(lessThan(20L), TimeUnit.SECONDS);
	}
}
