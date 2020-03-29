package org.springframework.clinicaetsii.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTests {

	@Autowired
	protected UserService userService;

	@Test
	void shouldFindUserByUsername() {

		assertThat(this.userService.findUserByUsername("doctor1")).isNotNull();
		assertThat(this.userService.findUserByUsername("administrative1")).isNotNull();
		Assertions.assertThat(this.userService.findUserByUsername("patient1")).isNotNull();
	}
	
	@Test
	void shouldNotFindUser() {

		assertThat(this.userService.findUserByUsername("")).isNull();
		assertThat(this.userService.findUserByUsername("none")).isNull();
	}

}
