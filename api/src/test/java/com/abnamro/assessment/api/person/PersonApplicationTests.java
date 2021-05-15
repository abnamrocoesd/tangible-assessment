package com.abnamro.assessment.api.person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonApplication.class)
@AutoConfigureMockMvc
class PersonApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getPesons_200() throws Exception {
		mvc.perform( MockMvcRequestBuilders
				.get("/persons")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getPesons_404() throws Exception {
		mvc.perform( MockMvcRequestBuilders
				.get("/person")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
