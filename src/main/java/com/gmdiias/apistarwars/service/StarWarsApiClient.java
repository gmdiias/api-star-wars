package com.gmdiias.apistarwars.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.gmdiias.apistarwars.dto.PageableApiDTO;
import com.gmdiias.apistarwars.dto.PlanetApDTO;
import com.gmdiias.apistarwars.exception.ServiceException;

@Service
public class StarWarsApiClient {

	private static final Logger LOG = LogManager.getLogger(StarWarsApiClient.class);
	private static final String URL_SWAPI_API = "https://swapi.dev/api/";
	private WebClient webClient = WebClient.create(URL_SWAPI_API);

	public PlanetApDTO getPlanetByName(String name) throws ServiceException {
		PageableApiDTO retorno = performsRequestToExternalApi(name);

		if (retorno.getCount() > 1) {
			throw new ServiceException("Mais de um planeta encontrado a API do Star Wars com esse nome.");
		}
		return retorno.getResults().stream().findFirst()
				.orElseThrow(() -> new ServiceException(
						"Nenhum planeta encontrado encontrado na API do Star Wars com esse nome."));
	}

	public PageableApiDTO findAllPlanets() throws ServiceException {
		return performsRequestToExternalApi("");
	}

	private PageableApiDTO performsRequestToExternalApi(String name) throws ServiceException {
		try {
			return webClient.get().uri(builder -> builder.path("planets/").queryParam("search", name).build())
					.retrieve().bodyToMono(PageableApiDTO.class).block();
		} catch (WebClientException e) {
			LOG.error("Ocorreu um erro ao realizar a requição. Erro: {}", e.getMessage());
			throw new ServiceException("Ocorreu um erro ao realizar a requisição. Erro: " + e.getMessage());
		}
	}
}
