package org.springframework.clinicaetsii.web.e2e.doctor;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Every.everyItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DoctorMedicamentoControllerE2ETests {

	private static Integer N_REGISTRO_1 = 51347;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
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

	@WithMockUser(value = "spring")
	@Test
	void anotherUserRoleShouldNotShowMedicamento() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/{nregistro}", N_REGISTRO_1))
				.andExpect(status().is(403));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void shouldNotShowMedicamento() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/{nregistro}", -1)).andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist("medicamento"))
				.andExpect(view().name("doctor/medicamentos/medicamentoDetails"));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	private void shouldInitFindForm() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/find")).andExpect(status().isOk())
				.andExpect(model().attributeExists("medicamento"))
				.andExpect(view().name("doctor/medicamentos/findMedicamentos"));
	}

	@WithMockUser(value = "spring")
	@Test
	private void anotherUserRoleShouldNotInitFindForm() throws Exception {
		this.mockMvc.perform(get("/doctor/medicamentos/find")).andExpect(status().is(403));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void shouldFindMedicamentos() throws Exception {
		this.mockMvc.perform(get(
				"/doctor/medicamentos?nombre={nombre}&pactivos={pactivos}&labtitular={labtitular}",
				"aspirina", "", "BAYER HISPANIA, S.L.")).andExpect(status().isOk())
				.andExpect(model().attributeExists("consultaMedicamento"))
				.andExpect(model().attribute("consultaMedicamento",
						hasProperty("resultados", not(empty()))))
				.andExpect(model().attribute("consultaMedicamento", hasProperty("resultados",
						everyItem(hasProperty("nombre", containsStringIgnoringCase("aspirina"))))))
				.andExpect(view().name("doctor/medicamentos/medicamentosList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void anotherUserRoleShouldNotFindMedicamentos() throws Exception {
		this.mockMvc.perform(get(
				"/doctor/medicamentos?nombre={nombre}&pactivos={pactivos}&labtitular={labtitular}",
				"aspirina", "", "BAYER HISPANIA, S.L.")).andExpect(status().is(403));
	}

	@WithMockUser(username = "doctor1", authorities = {"doctor"})
	@Test
	void shouldNotFindMedicamentos() throws Exception {
		this.mockMvc.perform(get(
				"/doctor/medicamentos?nombre={nombre}&pactivos={pactivos}&labtitular={labtitular}",
				"no existente", "", "no existente")).andExpect(status().isOk())
				.andExpect(model().attributeExists("emptyList"))
				.andExpect(view().name("doctor/medicamentos/medicamentosList"));
	}
}
