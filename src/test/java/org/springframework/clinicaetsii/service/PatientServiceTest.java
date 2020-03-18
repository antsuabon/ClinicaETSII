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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class PatientServiceTest {

	@Autowired
	protected PatientService patientService;


	@Test
	void shouldFindPatientsFromCurrentDoctor() {
		//		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Davis");
		//		Assertions.assertThat(owners.size()).isEqualTo(2);
		//
		//		owners = this.ownerService.findOwnerByLastName("Daviss");
		//		Assertions.assertThat(owners.isEmpty()).isTrue();
	}

}
