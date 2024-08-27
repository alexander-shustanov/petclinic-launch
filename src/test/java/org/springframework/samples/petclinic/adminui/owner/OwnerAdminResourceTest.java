package org.springframework.samples.petclinic.adminui.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link OwnerAdminResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OwnerAdminResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {

	}

	@Test
	public void getList() throws Exception {
		mockMvc.perform(get("/adminui/owners")
				.param("firstNameContains", "")
				.param("lastNameContains", "")
				.param("telephoneContains", "")
				.param("pageNumber", "0")
				.param("pageSize", "0")
				.param("sort", "")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(SecurityMockMvcRequestPostProcessors.user("user")))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void getOne() throws Exception {
		mockMvc.perform(get("/adminui/owners/{0}", "0")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(SecurityMockMvcRequestPostProcessors.user("user")))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void getMany() throws Exception {
		mockMvc.perform(get("/adminui/owners/by-ids")
				.param("ids", "")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.with(SecurityMockMvcRequestPostProcessors.user("user")))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void create() throws Exception {
		String dto = """

			{
                    "id": 0,
                    "firstName": "",
                    "lastName": "",
                    "address": "",
                    "city": "",
                    "telephone": "",
                    "petIds": []
                }""";

        mockMvc.perform(post("/adminui/owners")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .content(dto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void patch() throws Exception {
        String patchNode = "[]";

        mockMvc.perform(MockMvcRequestBuilders.patch("/adminui/owners/{0}", "0")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .content(patchNode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void patchMany() throws Exception {
        String patchNode = "[]";

        mockMvc.perform(MockMvcRequestBuilders.patch("/adminui/owners")
                        .param("ids", "")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .content(patchNode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/adminui/owners/{0}", "0")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteMany() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/adminui/owners")
                        .param("ids", "")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(print());
}}
