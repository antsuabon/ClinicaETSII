
package org.springframework.clinicaetsii.service;

import java.time.LocalDateTime;
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
	void shouldListDoctorsSortedByServices() throws Exception {
		Collection<Examination> examinations =
				this.examinationService.findExaminationsSortedByStartDate(1);

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


	@Test
	void shouldInsertExamination() throws Exception {
		Collection<Examination> examinations = this.examinationService.findAllExaminations();
		int tamañoInicial = examinations.size();

		Examination examination1 = new Examination();
		examination1.setDescription("asdfg0");
		examination1.setStartTime(LocalDateTime.of(2021, 12, 12, 12, 12));
		Collection<Examination> examinationadded =
				this.consultationService.findConsultationById(1).getExaminations();
		examinationadded.add(examination1);
		this.consultationService.findConsultationById(1).setExaminations(examinationadded);
		this.examinationService.saveExamination(examination1);
		Assertions.assertThat(examination1.getId().longValue()).isNotEqualTo(0);
		int tamañoFinal = this.examinationService.findAllExaminations().size();
		Assertions.assertThat(tamañoFinal).isEqualTo(tamañoInicial + 1);

	}


}
