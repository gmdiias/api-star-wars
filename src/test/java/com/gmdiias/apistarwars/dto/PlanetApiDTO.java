package com.gmdiias.apistarwars.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

public class PlanetApiDTO {

	@Test
	public void te() {
		PlanetApDTO planet = new PlanetApDTO();
		planet.setFilms(Lists.list("A New Hope", "The Empire Strikes Back", "Return of the Jedi"));
		assertEquals(Long.valueOf(3), planet.getNumFilmAppearances());
	}
	
	@Test
	public void tea() {
		PlanetApDTO planet = new PlanetApDTO();
		assertEquals(Long.valueOf(0), planet.getNumFilmAppearances());
	}
}
