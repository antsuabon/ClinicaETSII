package org.springframework.clinicaetsii.web.doctor;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.clinicaetsii.model.Service;
import org.springframework.clinicaetsii.model.form.DoctorForm;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.UserService;
import org.springframework.clinicaetsii.web.validator.DoctorFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/doctor")
public class DoctorDoctorController {

	private DoctorService doctorService;

	private UserService userService;

	@Autowired
	public DoctorDoctorController(final DoctorService doctorService,
			final UserService userService) {
		this.doctorService = doctorService;
		this.userService = userService;
	}

	@GetMapping
	public ModelAndView showDoctor() {
		ModelAndView mav = new ModelAndView("doctor/doctorDetails");
		mav.addObject(this.doctorService.findCurrentDoctor());
		return mav;
	}

	@InitBinder
	@RequestMapping("/doctor/edit")
	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new DoctorFormValidator(this.doctorService, this.userService));
	}


	@ModelAttribute("allServices")
	public Collection<Service> populateAllServices() {
		return this.doctorService.findAllServices();
	}

	@GetMapping("/edit")
	public String initUpdateDoctorForm(final Model model) {
		Doctor doctorToUpdate = this.doctorService.findCurrentDoctor();

		DoctorForm doctorForm = new DoctorForm();
		doctorForm.setDoctor(doctorToUpdate);

		model.addAttribute(doctorForm);
		return "/doctor/updateDoctorForm";
	}



	@PostMapping("/edit")
	public String processUpdateDoctorForm(@Valid final DoctorForm doctorForm,
			final BindingResult result) {
		Doctor doctorToUpdate = this.doctorService.findCurrentDoctor();
		String oldUsername = String.valueOf(doctorToUpdate.getUsername());

		if (result.hasErrors()) {
			return "/doctor/updateDoctorForm";
		} else {
			BeanUtils.copyProperties(doctorForm.getDoctor(), doctorToUpdate, "id", "password",
					"enabled");
			if (doctorForm.getNewPassword() != null
					&& !StringUtils.isEmpty(doctorForm.getNewPassword())) {
				doctorToUpdate.setPassword(doctorForm.getNewPassword());
			}

			this.doctorService.save(doctorToUpdate);

			if (!oldUsername.equals(doctorToUpdate.getUsername())) {
				return "redirect:/logout";
			} else {
				return "redirect:/doctor";
			}

		}


	}
}
