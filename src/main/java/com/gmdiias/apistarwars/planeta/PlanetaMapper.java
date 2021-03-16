package com.gmdiias.apistarwars.planeta;

import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PlanetaMapper {

	public Planeta toPlaneta(PlanetaDTO dto);
	
	public PlanetaDTO toPlaneta(Planeta planeta);
	
}
