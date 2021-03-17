package com.gmdiias.apistarwars.planet;

import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PlanetMapper {

	public Planet toPlanet(PlanetDTO dto);
	
	public PlanetDTO toPlanet(Planet planet);
	
}
