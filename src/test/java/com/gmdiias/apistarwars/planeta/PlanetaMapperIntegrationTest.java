package com.gmdiias.apistarwars.planeta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlanetaMapperIntegrationTest {

	@Autowired
	private PlanetaMapper mapper;

	@Test
	public void dtoParaEntityTest() {
		PlanetaDTO planetaDto = new PlanetaDTO();
		planetaDto.setNome("Yavin IV");
		planetaDto.setClima("temperate, tropical");
		planetaDto.setTerreno("jungle, rainforests");

		Planeta planetaEntity = mapper.toPlaneta(planetaDto);
		assertEquals(planetaEntity.getNome(), planetaDto.getNome(), "Nome é diferente do informado.");
		assertEquals(planetaEntity.getClima(), planetaDto.getClima(), "Clima é diferente do esperado.");
		assertEquals(planetaEntity.getTerreno(), planetaDto.getTerreno(), "Terreno é diferente do esperado.");
	}
	
	@Test
	public void entityParaDtoTest() {
		Planeta planetaEntity = new Planeta();
		planetaEntity.setNome("Tatooine");
		planetaEntity.setClima("arid");
		planetaEntity.setTerreno("desert");
		
		PlanetaDTO planetaDto = mapper.toPlaneta(planetaEntity);
		assertEquals(planetaEntity.getNome(), planetaDto.getNome(), "Nome é diferente do informado.");
		assertEquals(planetaEntity.getClima(), planetaDto.getClima(), "Clima é diferente do esperado.");
		assertEquals(planetaEntity.getTerreno(), planetaDto.getTerreno(), "Terreno é diferente do esperado.");
	}

}
