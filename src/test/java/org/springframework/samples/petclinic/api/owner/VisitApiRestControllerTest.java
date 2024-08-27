package org.springframework.samples.petclinic.api.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link VisitApiRestController}
 */
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class VisitApiRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {

	}

	@Test
	public void getOne() throws Exception {
		mockMvc.perform(get("/api/visits/{0}", "Arexus0r9X"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void create() throws Exception {
		String dto = """
			{
			    "date": "2024-08-25",
			    "description": "Test Visit",
			    "petId": "GhR1HdJOCu",
			    "petOwnerId": "J1EMrQSP6i"
			}""";

		mockMvc.perform(post("/api/visits")
				.content(dto)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
