package org.springframework.clinicaetsii.web.doctor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.cima.ConsultaMedicamento;
import org.springframework.clinicaetsii.model.cima.Medicamento;
import org.springframework.clinicaetsii.service.CIMAService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorMedicamentoController {

	private CIMAService cimaService;

	@Autowired
	public DoctorMedicamentoController(final CIMAService cimaService) {
		this.cimaService = cimaService;
	}

	@GetMapping("/doctor/medicamentos/find")
	public String initFindForm(final Map<String, Object> model) {
		model.put("medicamento", new Medicamento());
		return "doctor/medicamentos/findMedicamentos";
	}

	@GetMapping("/doctor/medicamentos")
	public String processFindForm(final Medicamento medicamento, final BindingResult result, final Map<String, Object> model) {
		ConsultaMedicamento consultaMedicamento = this.cimaService.findMedicamentosByAttributes(medicamento.getNombre(), medicamento.getPactivos(), medicamento.getLabtitular());
		model.put("consultaMedicamento", consultaMedicamento);
		return "doctor/medicamentos/medicamentosList";
	}

	@GetMapping("/doctor/medicamentos/{nregistro}")
	public String showDetails(final String nregistro, final BindingResult result, final Map<String, Object> model) {
		Medicamento medicamento = this.cimaService.findMedicamentoByNregistro(nregistro);
		model.put("medicamento", medicamento);
		return "doctor/medicamentos/medicamentoDetails";
	}

}
