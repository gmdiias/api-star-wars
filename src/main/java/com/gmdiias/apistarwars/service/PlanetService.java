package com.gmdiias.apistarwars.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmdiias.apistarwars.dto.PlanetDTO;
import com.gmdiias.apistarwars.dto.PlanetStarWarsApiDTO;
import com.gmdiias.apistarwars.entity.Planet;
import com.gmdiias.apistarwars.exception.EntityNotFoundException;
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.mapper.PlanetMapper;
import com.gmdiias.apistarwars.repository.PlanetRepository;
import com.gmdiias.apistarwars.webclient.StarWarsApiClient;

@Service
public class PlanetService {

	private static final Logger LOG = LogManager.getLogger(PlanetService.class);
	
	@Autowired
	private PlanetMapper mapper;

	@Autowired
	private PlanetRepository repository;

	@Autowired
	private StarWarsApiClient swApiService;

	public PlanetDTO getById(Long id) {
		Optional<Planet> planet = repository.findById(id);
		if (planet.isEmpty()) {
			throw new EntityNotFoundException("Nenhuma entidade localizada com o ID informado.");
		}
		return mapper.toPlanet(planet.get());
	}

	public List<PlanetDTO> findAll() {
		return repository.findAll().stream().map(mapper::toPlanet).collect(Collectors.toList());
	}

	@Transactional
	public PlanetDTO post(PlanetDTO dto) {
		Planet planeta = mapper.toPlanet(dto);
		Long numFilmPlanetAppearances = searchNumFilmPlanetAppearances(planeta.getName());
		planeta.setNumFilmAppearances(numFilmPlanetAppearances);
		Planet planetaSaved = repository.save(planeta);
		return mapper.toPlanet(planetaSaved);
	}

	private Long searchNumFilmPlanetAppearances(String namePlanet) {
		try {
			PlanetStarWarsApiDTO planet = swApiService.getPlanetByName(namePlanet);
			return planet.getNumFilmAppearances();
		} catch (ServiceException e) {
			LOG.error("Erro ao realizar busca do planeta na API externa. Erro: {}", e.getMessage());
			throw new EntityNotFoundException(e.getMessage());
		}
	}

	@Transactional
	public void deleteById(Long id) {
		Optional<Planet> planet = repository.findById(id);

		if (planet.isEmpty()) {
			throw new EntityNotFoundException("Nenhuma entidade localizada com o ID informado.");
		}
		repository.delete(planet.get());
	}
}
