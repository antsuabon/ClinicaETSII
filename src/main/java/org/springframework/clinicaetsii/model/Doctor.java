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

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Ken Krebs
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "doctor_id")
public class Doctor extends User {

	@Column(name = "collegiate_code")
	@NotEmpty
	private String collegiateCode;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "doctor_services", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
	@NotNull
	private Collection<Service> services;

}
