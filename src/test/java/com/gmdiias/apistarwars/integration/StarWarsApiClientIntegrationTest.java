package com.gmdiias.apistarwars.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmdiias.apistarwars.ApiStarWarsApplicationTests;
import com.gmdiias.apistarwars.dto.PageableApiDTO;
import com.gmdiias.apistarwars.dto.PlanetApDTO;
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.service.StarWarsApiClient;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

@SpringBootTest
public class StarWarsApiClientIntegrationTest extends ApiStarWarsApplicationTests {

	private MockWebServer mockWebServer;

	@Autowired
	private StarWarsApiClient starWarsService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() throws IOException {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.play();
	}

	@Test
	void requestWithInternalServerErrorTest() throws ServiceException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 500 Internal server error"));

		Exception exception = assertThrows(ServiceException.class, () -> {
			starWarsService.getPlanetByName("Tatooine");
		});
		String messageExpected = "Erro: 500 Internal Server";
		assertTrue(exception.getMessage().contains(messageExpected));
	}

	@Test
	void requestByNamePlanetWithNoEntityTest() throws ServiceException, JsonProcessingException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		PageableApiDTO serverReturn = new PageableApiDTO();
		serverReturn.setCount(0l);
		serverReturn.setResults(Lists.emptyList());

		mockWebServer.enqueue(new MockResponse().setResponseCode(200).addHeader("Content-Type", "application/json")
				.setBody(objectMapper.writeValueAsString(serverReturn)));

		Exception exception = assertThrows(ServiceException.class, () -> {
			starWarsService.getPlanetByName("Tatooine");
		});
		String messageExpected = "Nenhum planeta encontrado encontrado na API do Star Wars com esse nome.";
		assertTrue(exception.getMessage().equals(messageExpected));
	}
	
	@Test
	public void requestByNamePlanetWithEntityTest() throws ServiceException, JsonProcessingException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		PlanetApDTO planet = new PlanetApDTO();
		planet.setName("Tatooine");

		PageableApiDTO serverReturn = new PageableApiDTO();
		serverReturn.setCount(1l);
		serverReturn.setResults(Lists.list(planet));

		mockWebServer.enqueue(new MockResponse().setResponseCode(200).addHeader("Content-Type", "application/json")
				.setBody(objectMapper.writeValueAsString(serverReturn)));

		PlanetApDTO retorno = starWarsService.getPlanetByName("Tatooine");
		assertNotNull(retorno);
		assertEquals(planet.getName(), retorno.getName());
	}

	@Test
	public void requestByNamePlanetWithTwoEntityTest() throws ServiceException, JsonProcessingException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		PlanetApDTO planetTatooiene = new PlanetApDTO();
		planetTatooiene.setName("Tatooine");

		PlanetApDTO planet2 = new PlanetApDTO();
		planetTatooiene.setName("Tat");

		PageableApiDTO serverReturn = new PageableApiDTO();
		serverReturn.setCount(2l);
		serverReturn.setResults(Lists.list(planetTatooiene, planet2));

		mockWebServer.enqueue(new MockResponse().setResponseCode(200).addHeader("Content-Type", "application/json")
				.setBody(objectMapper.writeValueAsString(serverReturn)));

		Exception exception = assertThrows(ServiceException.class, () -> {
			starWarsService.getPlanetByName("T");
		});
		String messageExpected = "Mais de um planeta encontrado a API do Star Wars com esse nome.";
		assertTrue(exception.getMessage().equals(messageExpected));
	}
	
	@Test
	void requestWithIanternalServerErrorTest() throws ServiceException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 500 Internal server error"));

		Exception exception = assertThrows(ServiceException.class, () -> {
			starWarsService.findAllPlanets();
		});
		String messageExpected = "Erro: 500 Internal Server";
		assertTrue(exception.getMessage().contains(messageExpected));
	}

}
