package com.gmdiias.apistarwars.planeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetaService  {

	@Autowired
	private PlanetaRepository repository;
}
