package com.gmdiias.apistarwars.planet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmdiias.apistarwars.ApiStarWarsApplicationTests;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PlanetResourceTest extends ApiStarWarsApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void postPlanetTest() throws Exception {
		PlanetDTO planet = new PlanetDTO();
		planet.setName("Yavin IV");
		planet.setClimate("temperate, tropical");
		planet.setTerrain("jungle, rainforests");

		MvcResult result = mvc.perform(post("/planet/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planet)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

		PlanetDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetDTO.class);
		assertNotNull(response.getId(), "Entidade não possui ID, então não foi salva corretamente.");
		assertEquals(planet.getName(), response.getName(), "Nome do planeta salvo é diferente do esperado.");
	}

	@Test
	@Sql("/dataSetPlanetas.sql")
	public void getPlanetByIdTest() throws Exception {
		MvcResult result = mvc.perform(get("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetDTO.class);
		
		assertEquals(1, response.getId());
		assertEquals("Yavin IV", response.getName(), "Nome do planeta salvo é diferente do esperado.");
	}
	
	@Test
	public void getPlanetByIdNaoExistenteTest() throws Exception {
		mvc.perform(get("/planet/{id}", 99)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Sql("/dataSetPlanetas.sql")
	public void deletePlanetByIdTest() throws Exception {
		MvcResult result = mvc.perform(get("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetDTO.class);
		
		assertEquals(1, response.getId());
		assertEquals("Yavin IV", response.getName(), "Nome do planeta salvo é diferente do esperado.");
		
		mvc.perform(delete("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		
		mvc.perform(get("/planet/{id}", 1)).andExpect(status().isBadRequest()).andExpect(content().string("Entidade não localizada."));
	}

}
