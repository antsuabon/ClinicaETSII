package org.springframework.clinicaetsii.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Column(name = "username")
	@Id
	private String username;

	@Column(name = "password")
	@NotEmpty
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@Column(name = "surname")
	@NotEmpty
	private String surname;

	@Column(name = "dni")
	@NotEmpty
	private String dni;

	@Column(name = "email")
	@NotEmpty
	private String email;

	@Column(name = "phone")
	@NotEmpty
	private String phone;


	// TODO Propiedad derivada fullName
	public String getFullName() {
		return null;
	}
}
