package com.gmdiias.apistarwars.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

public class PlanetApiDTO {

	@Test
	public void planetListOfFilmsToNumberTest() {
		PlanetStarWarsApiDTO planet = new PlanetStarWarsApiDTO();
		planet.setFilms(Lists.list("A New Hope", "The Empire Strikes Back", "Return of the Jedi"));
		assertEquals(Long.valueOf(3), planet.getNumFilmAppearances());
	}
	
	@Test
	public void planetListOfFilmsNullToNumberTest() {
		PlanetStarWarsApiDTO planet = new PlanetStarWarsApiDTO();
		assertEquals(Long.valueOf(0), planet.getNumFilmAppearances());
	}
}
