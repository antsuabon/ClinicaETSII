package org.springframework.clinicaetsii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.form.Dashboard;
import org.springframework.clinicaetsii.repository.DashboardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {


	private DashboardRepository dashboardRepository;

	@Autowired
	public DashboardService(final DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}

	@Transactional(readOnly = true)
	public Dashboard getDashboard() {
		Dashboard dashboard = new Dashboard();

		dashboard.setAverageAge(this.dashboardRepository.getAverageAge());

		return dashboard;
	}


}
