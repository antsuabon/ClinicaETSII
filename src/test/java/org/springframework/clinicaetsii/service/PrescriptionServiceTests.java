/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.clinicaetsii.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PrescriptionServiceTests {

	@Autowired
	protected PrescriptionService prescriptionService;


	@Test
	void shouldFindPrescriptionsByPatientId() {

		int patientId1 = 4;
		Collection<Prescription> prescriptions1 = this.prescriptionService.findPrescriptionsByPatientId(patientId1);
		boolean condition = true;

		for (Prescription prescription : prescriptions1) {

			if (!(prescription.getPatient().getId() == patientId1)) {
				condition = false;
				break;

			}

		}

		Assertions.assertThat(condition).isTrue();

		int patientId2 = -1;
		Collection<Prescription> prescriptions2 = this.prescriptionService.findPrescriptionsByPatientId(patientId2);
		Assertions.assertThat(prescriptions2).isEmpty();

	}

	@Test
	void shouldFindPrescriptionById() {
		int prescriptionId1 = 1;
		Prescription prescription1 = this.prescriptionService.findPrescriptionById(prescriptionId1);
		Assertions.assertThat(prescription1.getId() == prescriptionId1).isTrue();

		int prescriptionId2 = -1;
		Prescription prescription2 = this.prescriptionService.findPrescriptionById(prescriptionId2);
		Assertions.assertThat(prescription2).isEqualTo(null);
	}

	@Test
	void shouldDeletePrescription() {

		int prescriptionId1 = 1;
		Prescription prescription1 = this.prescriptionService.findPrescriptionById(prescriptionId1);
		this.prescriptionService.deletePrescription(prescription1);
		Prescription prescriptionDeleted = this.prescriptionService.findPrescriptionById(prescriptionId1);
		Assertions.assertThat(prescriptionDeleted).isEqualTo(null);

	}

}
