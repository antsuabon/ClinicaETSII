
package org.springframework.clinicaetsii.service;

import java.util.ArrayList; 
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Examination;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ExplorationServiceTests {

	@Autowired
	protected ExaminationService examinationService;
	@Autowired
	protected ConsultationService consultationService;


	@Test
	void shouldListDoctorsSortedByServices() {
		Collection<Examination> examinations = this.examinationService.findExaminationsSortedByStartDate(1);
		
		Assertions.assertThat(examinations.size()).isEqualTo(2);

		List<Examination> listExaminations = new ArrayList<>(examinations);
		Examination firstExamination = listExaminations.get(0);
		Examination lastExamination = listExaminations.get(listExaminations.size() - 1);
		Boolean sortedFirst = true;
		Boolean sortedLast = true;
		for (Examination d : listExaminations) {
			if (d.getStartTime().isBefore(firstExamination.getStartTime())) {
				sortedFirst = false;
				break;
			}
			if (d.getStartTime().isAfter(lastExamination.getStartTime())) {
				sortedLast = false;
				break;
			}
		}
		Assertions.assertThat(sortedFirst && sortedLast).isEqualTo(true);

	}

}