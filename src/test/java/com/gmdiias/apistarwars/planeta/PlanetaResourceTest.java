package com.gmdiias.apistarwars.planeta;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanetaResourceTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void asTest() throws Exception {
		
		
		PlanetaDTO planeta = new PlanetaDTO();
		planeta.setNome("Yavin IV");
		planeta.setClima("temperate, tropical");
		planeta.setTerreno("jungle, rainforests");
		
		
		
		
		mvc.perform(get("/planeta/echo")).andExpect(status().isOk());
	}

}
