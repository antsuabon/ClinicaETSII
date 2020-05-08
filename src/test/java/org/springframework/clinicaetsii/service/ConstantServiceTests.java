package org.springframework.clinicaetsii.service;

import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ConstantServiceTests {

	@Autowired
	protected ConstantService constantService;

	@Test
	void shouldFindConstantById() {
		Constant constant = this.constantService.findConstantById(1);
		Assertions.assertThat(constant.getId()).isEqualTo(1);
		Assertions.assertThat(constant.getConstantType()).isNotNull();
		Assertions.assertThat(constant.getConstantType().getId()).isEqualTo(1);
		Assertions.assertThat(constant.getConstantType().getName()).isEqualTo("Peso");
		Assertions.assertThat(constant.getValue()).isEqualTo(75f);
	}

	@Test
	void shouldFindAllConstantTypes() {
		Collection<ConstantType> constantTypes = this.constantService.findAllConstantTypes();
		Assertions.assertThat(constantTypes).hasSize(19);
		Assertions.assertThat(constantTypes).allMatch(c -> c.getId() != null);
		Assertions.assertThat(constantTypes).allMatch(c -> c.getName().length() >= 3);
	}

	@Test
	@Transactional
	void shouldInsertConstant() {
		Constant constant = new Constant();
		constant.setValue(75f);
		ConstantType constantType = new ConstantType();
		constantType.setId(6);
		constantType.setName("FrecCard");
		constant.setConstantType(constantType);

		this.constantService.saveConstant(constant);
		Assertions.assertThat(constant.getId()).isNotNull();
		Assertions.assertThat(constant.getId()).isNotEqualTo(0);

		constant = this.constantService.findConstantById(constant.getId());
		Assertions.assertThat(constant).isNotNull();
		Assertions.assertThat(constant.getConstantType()).isNotNull();
		Assertions.assertThat(constant.getConstantType()).isEqualTo(constantType);
	}

	@Test
	@Transactional
	void shouldDeleteConstant() {
		Constant constant = this.constantService.findConstantById(1);
		Assertions.assertThat(constant).isNotNull();

		this.constantService.deleteConstant(constant);

		constant = this.constantService.findConstantById(1);
		Assertions.assertThat(constant).isNull();
	}

	@Test
	@Transactional
	void shouldUpdateConstant() {
		Constant constant = this.constantService.findConstantById(1);
		constant.setValue(80f);
		ConstantType constantType = new ConstantType();
		constantType.setId(6);
		constantType.setName("FrecCard");
		constant.setConstantType(constantType);

		this.constantService.saveConstant(constant);

		constant = this.constantService.findConstantById(1);
		Assertions.assertThat(constant).isNotNull();
		Assertions.assertThat(constant.getConstantType()).isNotNull();
		Assertions.assertThat(constant.getConstantType()).isEqualTo(constantType);
		Assertions.assertThat(constant.getValue()).isEqualTo(80f);
	}

}
