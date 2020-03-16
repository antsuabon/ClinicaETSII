
package org.springframework.clinicaetsii.web.doctor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Medicine;
import org.springframework.clinicaetsii.model.Patient;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.MedicineService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.service.PrescriptionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor/patients/{patientId}/prescriptions")
public class DoctorPrescriptionController {

	private PrescriptionService	prescriptionService;
	private DoctorService		doctorService;
	private PatientService		patientService;
	private MedicineService		medicineService;


	@Autowired
	public DoctorPrescriptionController(final PrescriptionService prescriptionService, final DoctorService doctorService, final PatientService patientService, final MedicineService medicineService) {
		this.prescriptionService = prescriptionService;
		this.doctorService = doctorService;
		this.patientService = patientService;
		this.medicineService = medicineService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("patient")
	public Patient findPatient(@PathVariable("patientId") final int patientId) {
		return this.patientService.findPatientById(patientId);
	}

	@GetMapping(value = "/new")
	public String initCreationForm(final ModelMap model) {

		Prescription prescription = new Prescription();
		Collection<Medicine> medicines = this.medicineService.findAllMedicines();
		model.put("medicines", medicines);
		model.put("prescription", prescription);

		return "/doctor/prescriptions/createPrescriptionForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@PathVariable("patientId") final int patientId, @ModelAttribute("medicine") final Medicine medicine, final Prescription prescription, final BindingResult result, final ModelMap model) {

		Collection<Medicine> medicines = this.medicineService.findAllMedicines();
		model.put("medicines", medicines);

		if (result.hasErrors()) {
			return "/doctor/prescriptions/createPrescriptionForm";
		} else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Doctor currentD = this.doctorService.findDoctorByUsername(auth.getName());
			Patient currentP = this.findPatient(patientId);
			prescription.setStartDate(LocalDateTime.now());
			prescription.setDoctor(currentD);
			prescription.setPatient(currentP);
			prescription.setMedicine(medicine);

			this.prescriptionService.savePrescription(prescription);
			return "redirect:/";
		}
	}

	@GetMapping
	public String listPrescriptionPatient(@PathVariable("patientId") final int patientId, final Map<String, Object> model) {
		Collection<Prescription> results = this.prescriptionService.findPrescriptionsByPatientId(patientId);

		if (results.isEmpty()) {
			model.put("emptyList", true);
		} else {
			model.put("patientId", patientId);
			model.put("prescriptions", results);
		}

		return "/doctor/prescriptions/prescriptionsList";
	}

	@GetMapping("/{prescriptionId}")
	public String showConsultationDetails(@PathVariable("prescriptionId") final int prescriptionId, final Map<String, Object> model) {
		Prescription result = this.prescriptionService.findPrescriptionById(prescriptionId);

		if (result == null) {
			model.put("empty", true);
		} else {
			model.put("prescription", result);
		}

		return "/doctor/prescriptions/prescriptionDetails";
	}

	@GetMapping("/{prescriptionId}/delete")
	public String initDelete(@PathVariable("prescriptionId") final int prescriptionId, final Map<String, Object> model) {

		Prescription prescription = this.prescriptionService.findPrescriptionById(prescriptionId);

		this.prescriptionService.deletePrescription(prescription);

		DoctorPatientController c = new DoctorPatientController(this.patientService);

		return c.listPatients(model);
	}
}
