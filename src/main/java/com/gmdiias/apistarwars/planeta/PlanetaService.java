package com.gmdiias.apistarwars.planeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetaService  {

	@Autowired
	private PlanetaMapper mapper;
	
	@Autowired
	private PlanetaRepository repository;
	
	public PlanetaDTO salvaPlaneta(PlanetaDTO dto) {
		Planeta planeta = mapper.toPlaneta(dto);
		return mapper.toPlaneta(repository.save(planeta));
	}
}
