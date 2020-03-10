/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.clinicaetsii.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Ken Krebs
 */
@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "patient_id")
public class Patient extends User {

	@Column(name = "nss")
	@NotBlank
	private String nss;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Past
	private LocalDate birthDate;

	@Column(name = "phone2")
	@NotBlank
	private String phone2;

	@Column(name = "address")
	@NotBlank
	private String address;

	@Column(name = "state")
	@NotBlank
	private String state;

	@ManyToOne(optional = false)
	@JoinColumn(name = "general_practitioner_id")
	@NotNull
	private Doctor generalPractitioner;
	
	public Doctor getGeneralPractitioner() {
		return this.generalPractitioner;
	}
	
	public void setGeneralPractitioner(Doctor generalPractitioner) {
		this.generalPractitioner = generalPractitioner;
	}

}
