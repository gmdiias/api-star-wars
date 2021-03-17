package com.gmdiias.apistarwars.webclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.planet.PlanetDTO;

@Service
public class StarWarsRestAPiService {

	private static final Logger LOG = LogManager.getLogger(StarWarsRestAPiService.class);
	private static final String URL_SWAPI_API = "https://swapi.dev/api/";
	private WebClient webClient = WebClient.create(URL_SWAPI_API);

	public PlanetDTO getPlanetByName(String name) throws ServiceException {
		PageableDTO retorno = realizaRequisicaoWithFilter(name);

		if (retorno.getCount() > 1) {
			throw new ServiceException("Mais de um planeta encontrado a API do Star Wars com esse nome.");
		}
		return retorno.getResults().stream().findFirst()
				.orElseThrow(() -> new ServiceException(
						"Nenhum planeta encontrado encontrado na API do Star Wars com esse nome."));
	}

	public PageableDTO getAllPlanets() throws ServiceException {
		return realizaRequisicaoWithFilter("");
	}

	private PageableDTO realizaRequisicaoWithFilter(String name) throws ServiceException {
		try {
			return webClient.get().uri(builder -> builder.path("planets/").queryParam("search", name).build())
					.retrieve().bodyToMono(PageableDTO.class).block();
		} catch (WebClientException e) {
			LOG.error("Ocorreu um erro ao realizar a requição. Erro: {}", e.getMessage());
			throw new ServiceException("Ocorreu um erro ao realizar a requisição. Erro: " + e.getMessage());
		}
	}
}
