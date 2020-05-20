package org.springframework.clinicaetsii.web.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.form.Dashboard;
import org.springframework.clinicaetsii.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class AdminDashboardController {

	private DashboardService dashboardService;

	public void initBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public AdminDashboardController(final DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/admin/dashboard")
	public String showDashboard(final Map<String, Object> model) {

		Dashboard dashboard = this.dashboardService.getDashboard();

		model.put("dashboard", dashboard);

		return "/admin/dashboard/show";
	}

}
