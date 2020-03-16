package org.springframework.clinicaetsii.web.doctor;

import java.time.LocalDateTime; 
import java.util.Collection;


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

	private PrescriptionService prescriptionService;
	private DoctorService doctorService;
	private PatientService patientService;
	private MedicineService medicineService;
	
	@Autowired
	public DoctorPrescriptionController(final PrescriptionService prescriptionService, DoctorService doctorService, PatientService patientService, MedicineService medicineService) {
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
	public Patient findPatient(@PathVariable("patientId") int patientId) {
		return this.patientService.findPatientById(patientId);
	}
	

	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {


		Prescription prescription = new Prescription();
		Collection<Medicine> medicines = this.medicineService.findAllMedicines();
		model.put("medicines", medicines);
		model.put("prescription", prescription);
		
		return "/doctor/prescriptions/createPrescriptionForm";
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@PathVariable("patientId") int patientId, @ModelAttribute("medicine") Medicine medicine , Prescription prescription, BindingResult result, ModelMap model) {
		
		Collection<Medicine> medicines = this.medicineService.findAllMedicines();
		model.put("medicines", medicines);
		
		if (result.hasErrors()) {
			return  "/doctor/prescriptions/createPrescriptionForm";
		}
		else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Doctor currentD = this.doctorService.findDoctorByUsername(auth.getName());
			Patient currentP = findPatient(patientId);
			prescription.setStartDate(LocalDateTime.now());
			prescription.setDoctor(currentD);
			prescription.setPatient(currentP);
			prescription.setMedicine(medicine);
			
			this.prescriptionService.savePrescription(prescription);
			return "redirect:/";
		}
	}
	
}
