package com.gmdiias.apistarwars.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gmdiias.apistarwars.ApiStarWarsApplicationTests;
import com.gmdiias.apistarwars.dto.PlanetDTO;
import com.gmdiias.apistarwars.entity.Planet;

@SpringBootTest
public class PlanetMapperTest extends ApiStarWarsApplicationTests {

	@Autowired
	private PlanetMapper mapper;

	@Test
	public void dtoToEntityTest() {
		PlanetDTO planetDto = new PlanetDTO();
		planetDto.setName("Yavin IV");
		planetDto.setClimate("temperate, tropical");
		planetDto.setTerrain("jungle, rainforests");
		planetDto.setNumFilmAppearances(Long.valueOf(3));

		Planet planetEntity = mapper.toPlanet(planetDto);
		assertEquals(planetEntity.getName(), planetDto.getName(), "Nome é diferente do informado.");
		assertEquals(planetEntity.getClimate(), planetDto.getClimate(), "Clima é diferente do esperado.");
		assertEquals(planetEntity.getTerrain(), planetDto.getTerrain(), "Terreno é diferente do esperado.");
		assertEquals(planetEntity.getNumFilmAppearances(), planetDto.getNumFilmAppearances(),
				"Quantidade de films é diferente do esperado");
	}

	@Test
	public void entityToDtoTest() {
		Planet planetEntity = new Planet();
		planetEntity.setName("Tatooine");
		planetEntity.setClimate("arid");
		planetEntity.setTerrain("desert");
		planetEntity.setNumFilmAppearances(Long.valueOf(3));

		PlanetDTO planetDto = mapper.toPlanet(planetEntity);
		assertEquals(planetEntity.getName(), planetDto.getName(), "Nome é diferente do informado.");
		assertEquals(planetEntity.getClimate(), planetDto.getClimate(), "Clima é diferente do esperado.");
		assertEquals(planetEntity.getTerrain(), planetDto.getTerrain(), "Terreno é diferente do esperado.");
		assertEquals(planetDto.getNumFilmAppearances(), planetEntity.getNumFilmAppearances(),
				"Quantidade de films é diferente do esperado");
	}

}
