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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link OwnerApiRestController}
 */
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class OwnerApiRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {

	}

	@Test
	public void findByTelephoneIn() throws Exception {
		String ownerKeyFieldsDtos = """

			[
                    {
                        "firstName": "Jean",
                        "lastName": "Coleman",
                        "telephone": "6085552654"
                    }
                ]""";

        mockMvc.perform(post("/api/owners/by-key-fields")
                        .content(ownerKeyFieldsDtos)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
}

	@Test
	public void findAllByOwner_Id() throws Exception {
		mockMvc.perform(get("/api/owners/{0}/pets", "J1EMrQSP6i")
				.param("pageNumber", "0")
				.param("pageSize", "10"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void findAllByPet_Id() throws Exception {
		mockMvc.perform(get("/api/owners/{0}/pets/{1}", "J1EMrQSP6i", "GhR1HdJOCu")
				.param("pageNumber", "0")
				.param("pageSize", "10"))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
