package com.gmdiias.apistarwars.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gmdiias.apistarwars.dto.PlanetRequestDTO;
import com.gmdiias.apistarwars.dto.PlanetStarWarsApiDTO;
import com.gmdiias.apistarwars.entity.Planet;
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.mapper.PlanetMapper;
import com.gmdiias.apistarwars.repository.PlanetRepository;

public class PlanetServiceTest {

	PlanetService planetService;

	private PlanetMapper mapper;
	private PlanetRepository repository;
	private StarWarsApiClientService swApiService;

	@BeforeEach
	void init() {
		mapper = mock(PlanetMapper.class);
		repository = mock(PlanetRepository.class);
		swApiService = mock(StarWarsApiClientService.class);
		planetService = new PlanetService(mapper, repository, swApiService);
	}

	@Test
	public void createPlanetWithSuccessTest() throws ServiceException {
		PlanetRequestDTO planetDto = new PlanetRequestDTO();
		planetDto.setName("Tatooine");

		Planet planetEntity = new Planet();
		planetEntity.setName("Tatooine");

		PlanetStarWarsApiDTO starWarsPlanetReturn = new PlanetStarWarsApiDTO();

		when(mapper.toPlanet(planetDto)).thenReturn(planetEntity);
		when(swApiService.getPlanetByName("Tatooine")).thenReturn(starWarsPlanetReturn);
		planetService.post(planetDto);

		verify(repository, times(1)).save(planetEntity);
		verify(swApiService, times(1)).getPlanetByName("Tatooine");
	}

	@Test
	public void createPlanetWithNameNotFoundInApiSwTest() throws ServiceException {
		PlanetRequestDTO planetDto = new PlanetRequestDTO();
		planetDto.setName("Tatooine");

		Planet planetEntity = new Planet();
		planetEntity.setName("Tatooine");

		when(mapper.toPlanet(planetDto)).thenReturn(planetEntity);
		when(swApiService.getPlanetByName("Tatooine")).thenThrow(
				new ServiceException("Nenhum planeta encontrado encontrado na API do Star Wars com esse nome."));
		
		Exception exception = assertThrows(ServiceException.class, () -> {
			planetService.post(planetDto);
		});
		
		String messageExpected = "Nenhum planeta encontrado encontrado na API do Star Wars com esse nome.";
		assertTrue(exception.getMessage().contains(messageExpected), "Mensagem da excessão não contém erro esperado.");
		
		verifyNoMoreInteractions(repository);
	}

}
