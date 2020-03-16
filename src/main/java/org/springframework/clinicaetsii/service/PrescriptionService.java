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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.clinicaetsii.model.Prescription;
import org.springframework.clinicaetsii.repository.PrescriptionRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrescriptionService {

	private PrescriptionRepository prescriptionRepository;


	@Autowired
	public PrescriptionService(final PrescriptionRepository prescriptionRepository) {
		this.prescriptionRepository = prescriptionRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Prescription> findPrescriptionsByPatientId(final int patientId) throws DataAccessException {
		return this.prescriptionRepository.findPrescriptionByPatientId(patientId);
	}

	@Transactional(readOnly = true)
	public Prescription findPrescriptionById(final int id) throws DataAccessException {
		return this.prescriptionRepository.findById(id);
	}

	@Transactional
	public void deletePrescription(final Prescription prescription) throws DataAccessException {
		this.prescriptionRepository.delete(prescription);
	}
}
