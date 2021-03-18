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
import com.gmdiias.apistarwars.dto.PlanetRequestDTO;
import com.gmdiias.apistarwars.dto.PlanetResponseDTO;
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.service.StarWarsApiClientService;
import com.gmdiias.apistarwars.dto.PlanetStarWarsApiDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PlanetIntegrationTest extends ApiStarWarsApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private StarWarsApiClientService planetService;

	@Test
	@Sql("/dataSetPlanetas.sql")
	public void getPlanetByIdTest() throws Exception {
		MvcResult response = mvc.perform(get("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetResponseDTO planet = objectMapper.readValue(response.getResponse().getContentAsString(), PlanetResponseDTO.class);

		assertEquals(1, planet.getId(), "ID é diferente do informado.");
		assertEquals("Yavin IV", planet.getName(), "Nome do planeta salvo é diferente do esperado.");
	}

	@Test
	public void getPlanetByIdNotExistTest() throws Exception {
		mvc.perform(get("/planet/{id}", 99)).andExpect(status().isBadRequest())
				.andExpect(content().string("Nenhuma entidade localizada com o ID informado."));
	}

	@Test
	@Sql("/dataSetPlanetas.sql")
	public void getPlanetByNameTest() throws Exception {
		MvcResult response = mvc.perform(get("/planet/name/{name}", "Yavin IV")).andExpect(status().isOk()).andReturn();
		PlanetResponseDTO planet = objectMapper.readValue(response.getResponse().getContentAsString(), PlanetResponseDTO.class);

		assertEquals(1, planet.getId(), "ID é diferente do informado.");
		assertEquals("Yavin IV", planet.getName(), "Nome do planeta salvo é diferente do esperado.");
	}

	@Test
	public void getPlanetByNameNotExistTest() throws Exception {
		mvc.perform(get("/planet/name/{name}", "Yavin IV")).andExpect(status().isBadRequest())
				.andExpect(content().string("Nenhuma entidade localizada com o nome informado."));
	}

	@Test
	@Sql("/dataSetPlanetas.sql")
	public void findAllTest() throws Exception {
		MvcResult response = mvc.perform(get("/planet/")).andExpect(status().isOk()).andReturn();
		List<PlanetResponseDTO> listPlanet = objectMapper.readValue(response.getResponse().getContentAsString(),
				new TypeReference<List<PlanetResponseDTO>>() {
				});
		assertEquals(2, listPlanet.size(), "Número de entidades retornas é diferente do esperado.");
	}

	@Test
	public void createPlanetTest() throws Exception {
		PlanetRequestDTO planet = new PlanetRequestDTO();
		planet.setName("Yavin IV");
		planet.setClimate("temperate, tropical");
		planet.setTerrain("jungle, rainforests");

		PlanetStarWarsApiDTO planetMock = new PlanetStarWarsApiDTO();
		planetMock.setFilms(Lists.list("http://swapi.dev/api/films/1/", "http://swapi.dev/api/films/2/",
				"http://swapi.dev/api/films/3/"));

		Mockito.when(planetService.getPlanetByName("Yavin IV")).thenReturn(planetMock);

		MvcResult response = mvc.perform(post("/planet/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planet)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

		PlanetResponseDTO planetResponse = objectMapper.readValue(response.getResponse().getContentAsString(), PlanetResponseDTO.class);
		assertNotNull(planetResponse.getId(), "Entidade não possui ID, então não foi salva corretamente.");
		assertEquals(planet.getName(), planetResponse.getName(), "Nome do planeta salvo é diferente do esperado.");
		assertEquals(Long.valueOf(3), planetResponse.getNumFilmAppearances(),
				"Número de filmes é diferente do esperado.");
	}

	@Test
	public void createDuplicatedPlanetTest() throws Exception {
		PlanetRequestDTO planet = new PlanetRequestDTO();
		planet.setName("Yavin IV");

		PlanetStarWarsApiDTO planetMock = new PlanetStarWarsApiDTO();
		planetMock.setFilms(Lists.list("http://swapi.dev/api/films/1/", "http://swapi.dev/api/films/2/",
				"http://swapi.dev/api/films/3/"));

		Mockito.when(planetService.getPlanetByName("Yavin IV")).thenReturn(planetMock);

		mvc.perform(post("/planet/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planet)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

		mvc.perform(post("/planet/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planet)).characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().string("Ja existe um Planeta cadastrado com as caracteristicas informadas."));

	}

	@Test
	public void createPlanetWithSwApiErrorTest() throws Exception {
		PlanetRequestDTO planet = new PlanetRequestDTO();
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
		MvcResult response = mvc.perform(get("/planet/{id}", 1)).andExpect(status().isOk()).andReturn();
		PlanetResponseDTO planet = objectMapper.readValue(response.getResponse().getContentAsString(), PlanetResponseDTO.class);

		assertEquals(1, planet.getId());
		assertEquals("Yavin IV", planet.getName(), "Nome do planeta salvo é diferente do esperado.");

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
