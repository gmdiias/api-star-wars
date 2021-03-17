package com.gmdiias.apistarwars.planeta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanetaResourceTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void postPlanetaTest() throws Exception {
		PlanetaDTO planeta = new PlanetaDTO();
		planeta.setNome("Yavin IV");
		planeta.setClima("temperate, tropical");
		planeta.setTerreno("jungle, rainforests");

		MvcResult result = mvc.perform(post("/planeta/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planeta)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

		PlanetaDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetaDTO.class);
		assertNotNull(response.getId(), "Entidade não possui ID, então não foi salva corretamente.");
		assertEquals(planeta.getNome(), response.getNome(), "Nome do planeta salvo é diferente do esperado.");
	}

	@Test
	@Sql("/dataSetPlanetas.sql")
	public void getPlanetaByIdTest() throws Exception {
		MvcResult result = mvc.perform(get("/planeta/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetaDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetaDTO.class);
		
		assertEquals(1, response.getId());
		assertEquals("Yavin IV", response.getNome(), "Nome do planeta salvo é diferente do esperado.");
	}
	
	@Test
	public void getPlanetaByIdNaoExistenteTest() throws Exception {
		mvc.perform(get("/planeta/{id}", 99)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Sql("/dataSetPlanetas.sql")
	public void deletePlanetaById() throws Exception {
		MvcResult result = mvc.perform(get("/planeta/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetaDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetaDTO.class);
		
		assertEquals(1, response.getId());
		assertEquals("Yavin IV", response.getNome(), "Nome do planeta salvo é diferente do esperado.");
		
		mvc.perform(delete("/planeta/{id}", 1)).andExpect(status().isOk()).andReturn();
		
		mvc.perform(get("/planeta/{id}", 1)).andExpect(status().isBadRequest()).andExpect(content().string("Entidade não localizada."));
	}

}
