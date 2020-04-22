package org.springframework.clinicaetsii.web.doctor;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Every.everyItem;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.cima.ConsultaMedicamento;
import org.springframework.clinicaetsii.model.cima.Excipiente;
import org.springframework.clinicaetsii.model.cima.Filtro;
import org.springframework.clinicaetsii.model.cima.Medicamento;
import org.springframework.clinicaetsii.model.cima.PrincipioActivo;
import org.springframework.clinicaetsii.service.CIMAService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DoctorMedicamentoController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
				classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class DoctorMedicamentoControllerTests {

	private static Integer N_REGISTRO_1 = 51347;

	@Autowired
	private DoctorMedicamentoController doctorMedicamentoController;

	@MockBean
	private CIMAService cimaService;

	@Autowired
	private MockMvc mockMvc;

	private Medicamento medicamento1;

	private ConsultaMedicamento consultaMedicamento1;
	private ConsultaMedicamento consultaMedicamento2;

	@BeforeEach
	private void setup() {

		this.medicamento1 = new Medicamento();
		this.medicamento1.setNombre("ASPIRINA C 400 mg/240 mg COMPRIMIDOS EFERVESCENTES");
		this.medicamento1.setPactivos("ASPIRINA C 400 mg/240 mg COMPRIMIDOS EFERVESCENTES");
		this.medicamento1.setLabtitular("Bayer Hispania, S.L.");
		this.medicamento1.setCpresc("Sin Receta");

		Filtro formaFarmaceutica = new Filtro();
		formaFarmaceutica.setNombre("COMPRIMIDO EFERVESCENTE");
		this.medicamento1.setFormaFarmaceutica(formaFarmaceutica);

		this.medicamento1.setDosis("400/240 mg/mg");

		PrincipioActivo pa1 = new PrincipioActivo();
		pa1.setNombre("ACETILSALICILICO ACIDO");
		pa1.setCantidad("400");
		pa1.setUnidad("mg");
		PrincipioActivo pa2 = new PrincipioActivo();
		pa2.setNombre("ASCORBICO ACIDO");
		pa2.setCantidad("240");
		pa2.setUnidad("mg");
		this.medicamento1.setPrincipiosActivos(Arrays.asList(pa1, pa2));

		Excipiente ex1 = new Excipiente();
		ex1.setNombre("HIDROGENO CARBONATO DE SODIO");
		ex1.setCantidad("900");
		ex1.setUnidad("mg");
		Excipiente ex2 = new Excipiente();
		ex2.setNombre("CARBONATO DE SODIO ANHIDRO");
		ex2.setCantidad("200");
		ex2.setUnidad("mg");
		Excipiente ex3 = new Excipiente();
		ex3.setNombre("CITRATO DE SODIO (E-331)");
		ex3.setCantidad("1242.0");
		ex3.setUnidad("mg");
		this.medicamento1.setExcipientes(Arrays.asList(ex1, ex2, ex3));

		Filtro viaAdministracion = new Filtro();
		viaAdministracion.setNombre("VÍA ORAL");
		this.medicamento1.setViasAdministracion(Arrays.asList(viaAdministracion));

		Filtro pr1 = new Filtro();
		pr1.setNombre("ASPIRINA C 400 mg/240 mg COMPRIMIDOS EFERVESCENTES , 10 comprimidos");
		Filtro pr2 = new Filtro();
		pr2.setNombre("ASPIRINA C 400 mg/240 mg COMPRIMIDOS EFERVESCENTES , 20 comprimidos");
		this.medicamento1.setPresentaciones(Arrays.asList(pr1, pr2));

		this.consultaMedicamento1 = new ConsultaMedicamento();
		this.consultaMedicamento1.setPagina(1);
		this.consultaMedicamento1.setTamanioPagina(25);
		this.consultaMedicamento1.setTotalFilas(1);
		this.consultaMedicamento1.setResultados(Arrays.asList(this.medicamento1));

		this.consultaMedicamento2 = new ConsultaMedicamento();
		this.consultaMedicamento2.setPagina(1);
		this.consultaMedicamento2.setTamanioPagina(25);
		this.consultaMedicamento2.setTotalFilas(0);
		this.consultaMedicamento2.setResultados(new ArrayList<>());

		given(this.cimaService.findMedicamentoByNregistro(N_REGISTRO_1))
				.willReturn(this.medicamento1);

		given(this.cimaService.findMedicamentosByAttributes("aspirina", "", "BAYER HISPANIA, S.L."))
				.willReturn(this.consultaMedicamento1);
		given(this.cimaService.findMedicamentosByAttributes("no existente", "", "no existente"))
				.willReturn(this.consultaMedicamento2);
	}

	@WithMockUser(username = "doctor1", roles = {"doctor"})
	@Test
	void shouldShowMedicamento() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/{nregistro}", N_REGISTRO_1))
				.andExpect(status().isOk()).andExpect(model().attributeExists("medicamento"))
				.andExpect(model().attribute("medicamento", hasProperty("nombre")))
				.andExpect(model().attribute("medicamento", hasProperty("pactivos")))
				.andExpect(model().attribute("medicamento", hasProperty("labtitular")))
				.andExpect(model().attribute("medicamento", hasProperty("cpresc")))
				.andExpect(model().attribute("medicamento", hasProperty("formaFarmaceutica")))
				.andExpect(model().attribute("medicamento", hasProperty("dosis")))
				.andExpect(model().attribute("medicamento",
						hasProperty("principiosActivos", not(empty()))))
				.andExpect(
						model().attribute("medicamento", hasProperty("excipientes", not(empty()))))
				.andExpect(model().attribute("medicamento",
						hasProperty("viasAdministracion", not(empty()))))
				.andExpect(model().attribute("medicamento",
						hasProperty("presentaciones", not(empty()))))
				.andExpect(view().name("doctor/medicamentos/medicamentoDetails"));
	}

	@WithMockUser(username = "doctor1", roles = {"doctor"})
	@Test
	void shouldNotShowMedicamento() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/{nregistro}", -1)).andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("medicamento"))
				.andExpect(view().name("doctor/medicamentos/medicamentoDetails"));
	}

	@WithMockUser(username = "doctor1", roles = {"doctor"})
	@Test
	private void shouldInitFindForm() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/find")).andExpect(status().isOk())
				.andExpect(model().attributeExists("medicamento"))
				.andExpect(view().name("doctor/medicamentos/findMedicamentos"));
	}

	@WithMockUser(username = "doctor1", roles = {"doctor"})
	@Test
	void shouldFindMedicamentos() throws Exception {
		this.mockMvc.perform(get(
				"/doctor/medicamentos?nombre={nombre}&pactivos={pactivos}&labtitular={labtitular}",
				"aspirina", "", "BAYER HISPANIA, S.L.").with(csrf())).andExpect(status().isOk())
				.andExpect(model().attributeExists("consultaMedicamento"))
				.andExpect(model().attribute("consultaMedicamento",
						hasProperty("resultados", not(empty()))))
				.andExpect(model().attribute("consultaMedicamento", hasProperty("resultados",
						everyItem(hasProperty("nombre", containsStringIgnoringCase("aspirina"))))))
				.andExpect(view().name("doctor/medicamentos/medicamentosList"));
	}

	@WithMockUser(username = "doctor1", roles = {"doctor"})
	@Test
	void shouldNotFindMedicamentos() throws Exception {
		this.mockMvc.perform(get(
				"/doctor/medicamentos?nombre={nombre}&pactivos={pactivos}&labtitular={labtitular}",
				"no existente", "", "no existente").with(csrf())).andExpect(status().isOk())
				.andExpect(model().attributeExists("consultaMedicamento"))
				.andExpect(model().attribute("consultaMedicamento",
						hasProperty("resultados", empty())))
				.andExpect(view().name("doctor/medicamentos/medicamentosList"));
	}
}
