package org.springframework.clinicaetsii.model;


import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.clinicaetsii.service.DoctorService;
import org.springframework.clinicaetsii.service.PatientService;
import org.springframework.clinicaetsii.web.validator.PatientValidator;
import org.springframework.context.annotation.ComponentScan;


@DataJpaTest(includeFilters = @ComponentScan.Filter(org.springframework.stereotype.Service.class))
@ExtendWith(MockitoExtension.class)
public class PatientValidatorTests {

	@Autowired
	protected DoctorService doctorService;

	@Autowired
	protected PatientService patientService;

	protected PatientValidator patientValidator;

	@BeforeEach
	void setup() {
		Doctor d = new Doctor();
		d.setName("Doctor");
		d.setSurname("Prueba");
		d.setCollegiateCode("303012456");
		d.setDni("43546587G");
		d.setEmail("algo@gmail.com");
		d.setPhone("657394567");
		d.setUsername("doctorP");
		d.setPassword("doctorP");
		d.setServices(new ArrayList<>());
		d.setId(1);
		this.doctorService.save(d);


		Patient p1 = new Patient();
		p1.setAddress("asdf");
		p1.setBirthDate(LocalDate.of(1999, 12, 12));
		p1.setDni("wtgwrtgwrtyh");
		p1.setEmail("algo@gmail.com");
		p1.setGeneralPractitioner(d);
		p1.setName("jose");
		p1.setNss("ertgwtgw");
		p1.setPassword("password");
		p1.setUsername("p1");
		p1.setPhone("897786678");
		p1.setSurname("asdfaf");
		p1.setPhone2("231423");
		p1.setState("aqwrefgr");
		p1.setId(1);
		this.patientService.savePatient(p1);

		Patient p2 = new Patient();
		p2.setAddress("asdf");
		p2.setBirthDate(LocalDate.of(1999, 12, 12));
		p2.setDni("fsgsfgbr");
		p2.setEmail("algo@gmail.com");
		p2.setGeneralPractitioner(d);
		p2.setName("jose");
		p2.setNss("yujrutyjr");
		p2.setPassword("password");
		p2.setUsername("p2");
		p2.setPhone("897786678");
		p2.setSurname("asdfaf");
		p2.setPhone2("231423");
		p2.setState("aqwrefgr");
		p2.setId(200);
		this.patientService.savePatient(p2);

		Patient p3 = new Patient();
		p3.setAddress("asdf");
		p3.setBirthDate(LocalDate.of(1999, 12, 12));
		p3.setDni("etrhwg");
		p3.setEmail("algo@gmail.com");
		p3.setGeneralPractitioner(d);
		p3.setName("jose");
		p3.setNss("sdfgrtghw");
		p3.setPassword("password");
		p3.setUsername("p3");
		p3.setPhone("897786678");
		p3.setSurname("asdfaf");
		p3.setPhone2("231423");
		p3.setState("aqwrefgr");
		p3.setId(300);
		this.patientService.savePatient(p3);

		Patient p4 = new Patient();
		p4.setAddress("asdf");
		p4.setBirthDate(LocalDate.of(1999, 12, 12));
		p4.setDni(" utehretg");
		p4.setEmail("algo@gmail.com");
		p4.setGeneralPractitioner(d);
		p4.setName("jose");
		p4.setNss("efgwertg");
		p4.setPassword("password");
		p4.setUsername("p4");
		p4.setPhone("897786678");
		p4.setSurname("asdfaf");
		p4.setPhone2("231423");
		p4.setState("aqwrefgr");
		p4.setId(4);
		this.patientService.savePatient(p4);

		Patient p5 = new Patient();
		p5.setAddress("asdf");
		p5.setBirthDate(LocalDate.of(1999, 12, 12));
		p5.setDni("wtrhrtyhwtg");
		p5.setEmail("algo@gmail.com");
		p5.setGeneralPractitioner(d);
		p5.setName("jose");
		p5.setNss("sdfgwtgw");
		p5.setPassword("password");
		p5.setUsername("p5");
		p5.setPhone("897786678");
		p5.setSurname("asdfaf");
		p5.setPhone2("231423");
		p5.setState("aqwrefgr");
		p5.setId(5);
		this.patientService.savePatient(p5);


	}

	// @Test
	// void shouldNotUpdateDoctorsLess5Patients() {
	// Doctor d = this.doctorService.findDoctorById(1);
	//
	//
	// Patient p6 = new Patient();
	// p6.setAddress("asdf");
	// p6.setBirthDate(LocalDate.of(1999, 12, 12));
	// p6.setDni("wertgrtgw");
	// p6.setEmail("algo@gmail.com");
	// p6.setGeneralPractitioner(d);
	// p6.setName("jose");
	// p6.setNss("yuikyutr");
	// p6.setPassword("password");
	// p6.setUsername("p6");
	// p6.setPhone("897786678");
	// p6.setSurname("asdfaf");
	// p6.setPhone2("231423");
	// p6.setState("aqwrefgr");
	// p6.setId(6);
	//
	// this.patientValidator = new PatientValidator(this.patientService);
	//
	// BindException errors = new BindException(p6, "patient6");
	//
	// this.patientValidator.validate(p6, errors);
	//
	// Assertions.assertThat(errors.hasFieldErrors("generalPractitioner")).isEqualTo(true);
	//
	// }

}
