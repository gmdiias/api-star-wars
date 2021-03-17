package com.gmdiias.apistarwars.webclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.gmdiias.apistarwars.planeta.PlanetaDTO;

@Component
public class WebClientRef {

	private static final String URL_SWAPI_API = "https://swapi.dev/api/";
	private WebClient webClient = WebClient.create(URL_SWAPI_API);

	public PlanetaDTO getPlanetaByNome(String nomePlaneta) {

		PageableDTO retorno = webClient.get()
				.uri(builder -> builder.path("planets/").queryParam("search", nomePlaneta).build()).retrieve()
				.bodyToMono(PageableDTO.class).block();

		if (retorno.getCount() < 1) {
			throw new RuntimeException("Nenhum planeta encontrado com esse nome.");
		}
		if (retorno.getCount() > 1) {
			throw new RuntimeException("Mais de um planeta encontrado com esse nome.");
		}
		return retorno.getResults().stream().findFirst().orElseThrow(RuntimeException::new);
	}
}
