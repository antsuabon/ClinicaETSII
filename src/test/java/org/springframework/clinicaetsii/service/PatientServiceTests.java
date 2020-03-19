package org.springframework.clinicaetsii.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Doctor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PatientServiceTests {

	@Autowired
	protected DoctorService doctorService;


	@Test
	void shouldListDoctorsServices() {
		
		Collection<Doctor> doctors = this.doctorService.findAllDoctors();

		List<Doctor> listDoctors = new ArrayList<>(doctors);
		boolean all = listDoctors.size() == 3;
		
		
		Assertions.assertThat(all);

	}
	
	
	
}
