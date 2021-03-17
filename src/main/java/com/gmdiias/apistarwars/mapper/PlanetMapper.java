package com.gmdiias.apistarwars.mapper;

import org.mapstruct.Mapper;

import com.gmdiias.apistarwars.dto.PlanetDTO;
import com.gmdiias.apistarwars.entity.Planet;

@Mapper(componentModel="spring")
public interface PlanetMapper {

	public Planet toPlanet(PlanetDTO dto);
	
	public PlanetDTO toPlanet(Planet planet);
	
}
