
package org.springframework.clinicaetsii.web;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.clinicaetsii.configuration.SecurityConfiguration;
import org.springframework.clinicaetsii.model.Constant;
import org.springframework.clinicaetsii.model.ConstantType;
import org.springframework.clinicaetsii.model.Consultation;
import org.springframework.clinicaetsii.service.AppointmentService;
import org.springframework.clinicaetsii.service.ConstantService;
import org.springframework.clinicaetsii.service.ConsultationService;
import org.springframework.clinicaetsii.web.doctor.DoctorConstantController;
import org.springframework.clinicaetsii.web.formatter.ConstantTypeFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DoctorConstantController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ConstantTypeFormatter.class), excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class DoctorConstantControllerTests {

	@Autowired
	private DoctorConstantController doctorConstantController;

	@MockBean
	private ConsultationService consultationService;

	@MockBean
	private ConstantService constantService;

	@MockBean
	private AppointmentService appointmentService;

	private Consultation		consultation1;

	private ConstantType		constantType1;
	private ConstantType		constantType2;
	private ConstantType		constantType6;
	private Collection<ConstantType> constantTypes;
	private Constant			constant1;
	private Constant			constant2;
	private Constant			constant1ToUpdate;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	private void initConstants() {
		this.constantType1 = new ConstantType();
		this.constantType1.setId(1);
		this.constantType1.setName("Peso");

		this.constantType2 = new ConstantType();
		this.constantType2.setId(2);
		this.constantType2.setName("IMC");

		this.constantType6 = new ConstantType();
		this.constantType6.setId(6);
		this.constantType6.setName("FrecCard");

		this.constant1 = new Constant();
		this.constant1.setId(1);
		this.constant1.setConstantType(this.constantType1);
		this.constant1.setValue(52f);

		this.constant2 = new Constant();
		this.constant2.setId(2);
		this.constant2.setConstantType(this.constantType6);
		this.constant2.setValue(92f);


		Collection<Constant> constants = new ArrayList<>();
		constants.add(this.constant1);
		constants.add(this.constant2);

		this.constantTypes = new ArrayList<>();
		this.constantTypes.add(this.constantType1);
		this.constantTypes.add(this.constantType2);
		this.constantTypes.add(this.constantType6);

		this.consultation1 = new Consultation();
		this.consultation1.setId(1);
		this.consultation1.setConstants(constants);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitConstantCreation() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new", 1, 1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("constantTypes", this.constantTypes))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessConstantCreation() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		BDDMockito.given(this.consultationService.findConsultationById(1)).willReturn(this.consultation1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new", 1, 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("constantType", "2")
				.param("value", "20"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotProcessConstantCreation() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		BDDMockito.given(this.consultationService.findConsultationById(1)).willReturn(this.consultation1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patients/{patientId}/consultations/{consultationId}/constants/new", 1, 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("constantType", "1")
				.param("value", "-5"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("constant"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("constant", "constantType", "value"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitConstantEdition() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit", 1, 1, 1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("constantTypes", this.constantTypes))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessConstantEdition() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		BDDMockito.given(this.consultationService.findConsultationById(1)).willReturn(this.consultation1);
		BDDMockito.given(this.constantService.findConstantById(1)).willReturn(this.constant1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit", 1, 1, 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("constantType", "2")
				.param("value", "20"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testNotProcessConstantEdition() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		BDDMockito.given(this.consultationService.findConsultationById(1)).willReturn(this.consultation1);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/edit", 1, 1, 1)
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("constantType", "6")
				.param("value", "-5"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("constant"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("constant", "constantType", "value"))
			.andExpect(MockMvcResultMatchers.view().name("/doctor/consultations/createOrUpdateConstantForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessConstantDeletion() throws Exception {
		BDDMockito.given(this.constantService.findAllConstantTypes()).willReturn(this.constantTypes);
		BDDMockito.given(this.consultationService.findConsultationById(1)).willReturn(this.consultation1);
		BDDMockito.given(this.constantService.findConstantById(1)).willReturn(this.constant1);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/doctor/patients/{patientId}/consultations/{consultationId}/constants/{constantId}/delete", 1, 1, 1))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/doctor/patients/{patientId}/consultations/{consultationId}"));
	}
}
