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
	public void dtoParaEntityTest() {
		PlanetDTO planetDto = new PlanetDTO();
		planetDto.setName("Yavin IV");
		planetDto.setClimate("temperate, tropical");
		planetDto.setTerrain("jungle, rainforests");

		Planet planetEntity = mapper.toPlanet(planetDto);
		assertEquals(planetEntity.getName(), planetDto.getName(), "Nome é diferente do informado.");
		assertEquals(planetEntity.getClimate(), planetDto.getClimate(), "Clima é diferente do esperado.");
		assertEquals(planetEntity.getTerrain(), planetDto.getTerrain(), "Terreno é diferente do esperado.");
	}
	
	@Test
	public void entityParaDtoTest() {
		Planet planetEntity = new Planet();
		planetEntity.setName("Tatooine");
		planetEntity.setClimate("arid");
		planetEntity.setTerrain("desert");
		
		PlanetDTO planetDto = mapper.toPlanet(planetEntity);
		assertEquals(planetEntity.getName(), planetDto.getName(), "Nome é diferente do informado.");
		assertEquals(planetEntity.getClimate(), planetDto.getClimate(), "Clima é diferente do esperado.");
		assertEquals(planetEntity.getTerrain(), planetDto.getTerrain(), "Terreno é diferente do esperado.");
	}

}
