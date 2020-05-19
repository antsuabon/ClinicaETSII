package org.springframework.clinicaetsii.service;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Administrative;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdministrativeServiceTests {

	@Autowired
	protected AdministrativeService administrativeService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@WithMockUser(username = "admin", roles = {"admin"})
	void shouldListAdministratives() {
		Collection<Administrative> administratives =
				this.administrativeService.findAllAdministratives();
		Assertions.assertThat(administratives).isNotEmpty().hasSize(4);
	}

	@Test
	void adminShouldFindAdministrative() {
		Administrative administrative1 = this.administrativeService.findAdministrativeById(101);
		String username = administrative1.getUsername();

		Assertions.assertThat(username).contains("administrative");

		Administrative administrative2 = this.administrativeService.findAdministrativeById(-1);
		Assertions.assertThat(administrative2).isNull();

	}


	@Test
	@Transactional
	void shouldDeleteDoctor() {

		Administrative a = this.administrativeService.findAdministrativeById(101);
		Collection<Administrative> administratives1 =
				this.administrativeService.findAllAdministratives();
		Assertions.assertThat(administratives1).isNotNull().isNotEmpty();

		this.administrativeService.delete(a);
		Collection<Administrative> administratives2 =
				this.administrativeService.findAllAdministratives();
		Assertions.assertThat(administratives2).isNotNull();

		Assertions.assertThat(administratives2).hasSize(administratives1.size() - 1);

	}

}
