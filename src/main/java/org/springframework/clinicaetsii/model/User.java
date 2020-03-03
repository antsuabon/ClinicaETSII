
package org.springframework.clinicaetsii.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

	@Column(name = "username")
	@Id
	private String	username;

	@Column(name = "password")
	@NotBlank
	private String	password;

	@Column(name = "enabled")
	private boolean	enabled;

	@Column(name = "name")
	@NotBlank
	private String	name;

	@Column(name = "surname")
	@NotBlank
	private String	surname;

	@Column(name = "dni")
	@NotBlank
	private String	dni;

	@Column(name = "email")
	@NotBlank
	@Email
	private String	email;

	@Column(name = "phone")
	@NotBlank
	private String	phone;


	public String getFullName() {
		return this.getSurname() + ", " + this.getName();
	}
}
