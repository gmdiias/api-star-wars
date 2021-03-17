package com.gmdiias.apistarwars.planet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmdiias.apistarwars.exception.EntityNotFoundException;

@Service
@Transactional
public class PlanetService {

	@Autowired
	private PlanetMapper mapper;

	@Autowired
	private PlanetRepository repository;

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

	public PlanetDTO post(PlanetDTO dto) {
		Planet planeta = mapper.toPlanet(dto);
		Planet planetaSaved = repository.save(planeta);
		return mapper.toPlanet(planetaSaved);
	}

	public void deleteById(Long id) {
		Optional<Planet> planet = repository.findById(id);

		if (planet.isEmpty()) {
			throw new EntityNotFoundException("Entidade não localizada.");
		}
		repository.delete(planet.get());
	}
}
