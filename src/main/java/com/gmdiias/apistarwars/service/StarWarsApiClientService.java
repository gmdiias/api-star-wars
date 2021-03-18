package com.gmdiias.apistarwars.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.gmdiias.apistarwars.dto.PageableStarWarsApiDTO;
import com.gmdiias.apistarwars.dto.PlanetStarWarsApiDTO;
import com.gmdiias.apistarwars.exception.ServiceException;

@Service
public class StarWarsApiClientService {

	private static final Logger LOG = LogManager.getLogger(StarWarsApiClientService.class);
	private static final String URL_SWAPI_API = "https://swapi.dev/api/";
	private WebClient webClient = WebClient.create(URL_SWAPI_API);

	public PlanetStarWarsApiDTO getPlanetByName(String name) throws ServiceException {
		PageableStarWarsApiDTO retorno = performsRequestToExternalApi(name, 1);

		if (retorno.getCount() > 1) {
			throw new ServiceException("Mais de um planeta encontrado a API do Star Wars com esse nome.");
		}
		return retorno.getResults().stream().findFirst().orElseThrow(
				() -> new ServiceException("Nenhum planeta encontrado encontrado na API do Star Wars com esse nome."));
	}

	public PageableStarWarsApiDTO findAllPlanets(String filter, int page) throws ServiceException {
		return performsRequestToExternalApi(filter, page);
	}

	private PageableStarWarsApiDTO performsRequestToExternalApi(String name, int page) throws ServiceException {
		try {
			return webClient.get().uri(
					builder -> builder.path("planets/").queryParam("search", name).queryParam("page", page).build())
					.retrieve().bodyToMono(PageableStarWarsApiDTO.class).block();
		} catch (WebClientException e) {
			LOG.error("Ocorreu um erro ao realizar a requição. Erro: {}", e.getMessage());
			throw new ServiceException("Ocorreu um erro ao realizar a requisição. Erro: " + e.getMessage());
		}
	}
}
