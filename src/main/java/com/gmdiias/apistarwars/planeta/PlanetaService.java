package com.gmdiias.apistarwars.planeta;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmdiias.apistarwars.exception.EntidadeNaoEncontradaException;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaMapper mapper;

	@Autowired
	private PlanetaRepository repository;

	public PlanetaDTO getById(Long id) {
		Optional<Planeta> planeta = repository.findById(id);

		if (planeta.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Entidade não localizada.");
		}
		return mapper.toPlaneta(planeta.get());
	}

	public List<PlanetaDTO> findAll() {
		return repository.findAll().stream().map(mapper::toPlaneta).collect(Collectors.toList());
	}

	public PlanetaDTO post(PlanetaDTO dto) {
		Planeta planeta = mapper.toPlaneta(dto);
		Planeta planetaSaved = repository.save(planeta);
		return mapper.toPlaneta(planetaSaved);
	}

	public void deletaPlanetaById(Long id) {
		Optional<Planeta> planeta = repository.findById(id);

		if (planeta.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Entidade não localizada.");
		}
		repository.delete(planeta.get());
	}
}
