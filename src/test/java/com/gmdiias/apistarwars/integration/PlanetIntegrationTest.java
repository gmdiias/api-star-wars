package com.gmdiias.apistarwars.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmdiias.apistarwars.ApiStarWarsApplicationTests;
import com.gmdiias.apistarwars.dto.PlanetDTO;
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.dto.PlanetApDTO;
import com.gmdiias.apistarwars.service.StarWarsApiClient;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PlanetIntegrationTest extends ApiStarWarsApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private StarWarsApiClient planetService;

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
	public void findAllTest() throws Exception {
		MvcResult result = mvc.perform(get("/planet/")).andExpect(status().isOk()).andReturn();
		List<PlanetDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<PlanetDTO>>() {
				});
		assertEquals(2, response.size());
	}

	@Test
	public void postPlanetTest() throws Exception {
		PlanetDTO planet = new PlanetDTO();
		planet.setName("Yavin IV");
		planet.setClimate("temperate, tropical");
		planet.setTerrain("jungle, rainforests");

		PlanetApDTO planetMock = new PlanetApDTO();
		planetMock.setFilms(Lists.list("http://swapi.dev/api/films/1/", "http://swapi.dev/api/films/2/",
				"http://swapi.dev/api/films/3/"));

		Mockito.when(planetService.getPlanetByName("Yavin IV")).thenReturn(planetMock);

		MvcResult result = mvc.perform(post("/planet/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planet)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

		PlanetDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetDTO.class);
		assertNotNull(response.getId(), "Entidade não possui ID, então não foi salva corretamente.");
		assertEquals(planet.getName(), response.getName(), "Nome do planeta salvo é diferente do esperado.");
		assertEquals(Long.valueOf(3), response.getNumFilmAppearances());
	}

	@Test
	public void postPlanetWithApiErrorTest() throws Exception {
		PlanetDTO planet = new PlanetDTO();
		planet.setName("Yavin IV");
		planet.setClimate("temperate, tropical");
		planet.setTerrain("jungle, rainforests");

		String errorMessage = "Nenhum planeta encontrado encontrado na API do Star Wars com esse nome.";
		Mockito.when(planetService.getPlanetByName("Yavin IV")).thenThrow(new ServiceException(errorMessage));

		mvc.perform(post("/planet/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planet)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().string(errorMessage));
	}

	@Test
	@Sql("/dataSetPlanetas.sql")
	public void deletePlanetByIdTest() throws Exception {
		MvcResult result = mvc.perform(get("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PlanetDTO.class);

		assertEquals(1, response.getId());
		assertEquals("Yavin IV", response.getName(), "Nome do planeta salvo é diferente do esperado.");

		mvc.perform(delete("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		mvc.perform(get("/planet/{id}", 1)).andExpect(status().isBadRequest())
				.andExpect(content().string("Nenhuma entidade localizada com o ID informado."));
	}

	@Test
	public void deletePlanetByIdWithEntityNotExistsTest() throws Exception {
		mvc.perform(delete("/planet/{id}", 1)).andExpect(status().isBadRequest())
				.andExpect(content().string("Nenhuma entidade localizada com o ID informado."));

	}

}
