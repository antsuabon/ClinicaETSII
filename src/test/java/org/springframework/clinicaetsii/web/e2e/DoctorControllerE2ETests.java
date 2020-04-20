
package org.springframework.clinicaetsii.web.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DoctorControllerE2ETests {

	private static final int TEST_DOCTOR_ID_1 = 1;
	private static final int TEST_DOCTOR_ID_2 = 2;
	private static final int TEST_DOCTOR_ID_3 = 3;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(value = "spring")
	@Test
	void testListDoctors() throws Exception {
		this.mockMvc.perform(get("/anonymous/doctors")).andExpect(status().isOk())
				.andExpect(model().attributeExists("doctors"))
				.andExpect(view().name("/anonymous/doctors/doctorsList"));
	}

	// @WithMockUser(value = "spring")
	// @Test
	// void testNotListDoctors() throws Exception {
	// this.mockMvc.perform(get("/anonymous/doctors")).andExpect(status().isOk())
	// .andExpect(model().attributeExists("emptylist"))
	// .andExpect(view().name("/anonymous/doctors/doctorsList"));
	// }

}
