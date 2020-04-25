package org.springframework.clinicaetsii.web.doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.cima.ConsultaFiltro;
import org.springframework.clinicaetsii.model.cima.ConsultaMedicamento;
import org.springframework.clinicaetsii.model.cima.Filtro;
import org.springframework.clinicaetsii.model.cima.Medicamento;
import org.springframework.clinicaetsii.service.CIMAService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/doctor/medicamentos")
public class DoctorMedicamentoController {

	private CIMAService cimaService;

	@Autowired
	public DoctorMedicamentoController(final CIMAService cimaService) {
		this.cimaService = cimaService;
	}

	@GetMapping("/find")
	public String initFindForm(final Map<String, Object> model) {
		model.put("medicamento", new Medicamento());
		return "doctor/medicamentos/findMedicamentos";
	}

	@GetMapping
	public String processFindForm(final Medicamento medicamento,
			final BindingResult result,
			final Map<String, Object> model) {

		ConsultaMedicamento consultaMedicamento = this.cimaService.findMedicamentosByAttributes(
				medicamento.getNombre(), medicamento.getPactivos(), medicamento.getLabtitular());

		if (consultaMedicamento == null || consultaMedicamento.getResultados().isEmpty()) {
			model.put("emptyList", "true");
		} else {
			model.put("consultaMedicamento", consultaMedicamento);
		}

		return "doctor/medicamentos/medicamentosList";
	}

	@GetMapping("/{nregistro}")
	public String showDetails(@PathVariable final int nregistro, final Map<String, Object> model) {
		Medicamento medicamento = this.cimaService.findMedicamentoByNregistro(nregistro);
		model.put("medicamento", medicamento);
		return "doctor/medicamentos/medicamentoDetails";
	}

	@GetMapping("/principiosActivosAutocomplete")
	@ResponseBody
	public List<String> principiosActivosAutocomplete(
			@RequestParam(value = "term", required = false, defaultValue = "*") final String term) {
		List<String> suggestions = new ArrayList<>();
		ConsultaFiltro principiosActivos = this.cimaService.findPrincipiosActivosByNombre(term, 1);

		if (principiosActivos != null && principiosActivos.getResultados() != null) {
			for (Filtro result : principiosActivos.getResultados()) {
				suggestions.add(result.getNombre());
			}
		}

		return suggestions;
	}

	@GetMapping("/laboratoriosAutocomplete")
	@ResponseBody
	public List<String> laboratoriosAutocomplete(
			@RequestParam(value = "term", required = false, defaultValue = "*") final String term) {
		List<String> suggestions = new ArrayList<>();
		ConsultaFiltro laboratorios = this.cimaService.findLaboratoriosByNombre(term, 1);

		if (laboratorios != null && laboratorios.getResultados() != null) {
			for (Filtro result : laboratorios.getResultados()) {
				suggestions.add(result.getNombre());
			}
		}

		return suggestions;
	}

}
