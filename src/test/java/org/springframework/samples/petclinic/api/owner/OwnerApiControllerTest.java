package org.springframework.samples.petclinic.api.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link OwnerApiController}
 */
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class OwnerApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {

	}

	@Test
	public void findByTelephoneIn() throws Exception {
		String keyFieldDtos = """

			[
                    {
                        "firstName": "George",
                        "lastName": "Franklin",
                        "telephone": "6085551023"
                    }
                ]""";

        mockMvc.perform(post("/api/owners/by-key-fields")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.content(keyFieldDtos)
				.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
	}

	@Test
	public void findByOwner_Id() throws Exception {
		mockMvc.perform(get("/api/owners/{0}/pets", "CGH5QKD1IY")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void findAllByPet_Id() throws Exception {
		mockMvc.perform(get("/api/owners/{0}/pets/{1}/visits", "CGH5QKD1IY", "WrqV4LHUrm")
				.param("pageNumber", "0")
				.param("pageSize", "10")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
