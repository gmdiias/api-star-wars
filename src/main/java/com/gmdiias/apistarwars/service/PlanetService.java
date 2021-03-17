package com.gmdiias.apistarwars.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmdiias.apistarwars.dto.PlanetDTO;
import com.gmdiias.apistarwars.entity.Planet;
import com.gmdiias.apistarwars.exception.EntityNotFoundException;
import com.gmdiias.apistarwars.exception.ServiceException;
import com.gmdiias.apistarwars.mapper.PlanetMapper;
import com.gmdiias.apistarwars.repository.PlanetRepository;

@Service
public class PlanetService {

	@Autowired
	private PlanetMapper mapper;

	@Autowired
	private PlanetRepository repository;
	
	@Autowired
	private StarWarsRestAPiService swApiService;

	public PlanetDTO getById(Long id) {
		Optional<Planet> planet = repository.findById(id);

		if (planet.isEmpty()) {
			throw new EntityNotFoundException("Entidade não localizada.");
		}
		return mapper.toPlanet(planet.get());
	}

	public List<PlanetDTO> findAll() {
		return repository.findAll().stream().map(mapper::toPlanet).collect(Collectors.toList());
	}

	@Transactional
	public PlanetDTO post(PlanetDTO dto) {
		try {
			Planet planeta = mapper.toPlanet(dto);
			Long qtdFilms = swApiService.get(planeta.getName());
			planeta.setNumFilmAppearances(qtdFilms);
			Planet planetaSaved = repository.save(planeta);
			return mapper.toPlanet(planetaSaved);
		} catch (ServiceException e) {
			throw new EntityNotFoundException("Entidade não localizada.");
		}
		
	}

	@Transactional
	public void deleteById(Long id) {
		Optional<Planet> planet = repository.findById(id);

		if (planet.isEmpty()) {
			throw new EntityNotFoundException("Entidade não localizada.");
		}
		repository.delete(planet.get());
	}
}
