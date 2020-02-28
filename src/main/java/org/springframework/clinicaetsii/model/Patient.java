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

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Ken Krebs
 */
@Entity
@Table(name = "patients")
public class Patient extends User {

	@Column(name = "nss")
	@NotEmpty
	private String nss;

	@Column(name = "fechaNacimiento")
	@NotEmpty
	private Date fechaNacimiento;

	@Column(name = "phone2")
	@NotEmpty
	private String phone2;

	@Column(name = "address")
	@NotEmpty
	private String address;

	@Column(name = "state")
	@NotEmpty
	private String state;

}
