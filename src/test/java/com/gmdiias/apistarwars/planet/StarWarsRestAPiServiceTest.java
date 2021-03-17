package com.gmdiias.apistarwars.planet;

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
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.webclient.PageableDTO;
import com.gmdiias.apistarwars.webclient.StarWarsRestAPiService;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

@SpringBootTest
public class StarWarsRestAPiServiceTest extends ApiStarWarsApplicationTests {

	private MockWebServer mockWebServer;

	@Autowired
	private StarWarsRestAPiService starWarsService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() throws IOException {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.play();
	}

	@Test
	public void requestWithInternalServerErrorTest() throws ServiceException {

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
	public void requestByNamePlanetWithNoEntityTest() throws ServiceException, JsonProcessingException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		PageableDTO serverReturn = new PageableDTO();
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

		PlanetDTO planet = new PlanetDTO();
		planet.setName("Tatooine");

		PageableDTO serverReturn = new PageableDTO();
		serverReturn.setCount(1l);
		serverReturn.setResults(Lists.list(planet));

		mockWebServer.enqueue(new MockResponse().setResponseCode(200).addHeader("Content-Type", "application/json")
				.setBody(objectMapper.writeValueAsString(serverReturn)));

		PlanetDTO retorno = starWarsService.getPlanetByName("Tatooine");
		assertNotNull(retorno);
		assertEquals(planet.getName(), retorno.getName());
	}

	@Test
	public void requestByNamePlanetWithTwoEntityTest() throws ServiceException, JsonProcessingException {

		WebClient webCliente = WebClient.create(mockWebServer.getUrl("/").toString());
		ReflectionTestUtils.setField(starWarsService, "webClient", webCliente);

		PlanetDTO planetTatooiene = new PlanetDTO();
		planetTatooiene.setName("Tatooine");

		PlanetDTO planet2 = new PlanetDTO();
		planetTatooiene.setName("Tat");

		PageableDTO serverReturn = new PageableDTO();
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

}
