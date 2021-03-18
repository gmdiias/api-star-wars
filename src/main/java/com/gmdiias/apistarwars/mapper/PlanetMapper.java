package com.gmdiias.apistarwars.mapper;

import org.mapstruct.Mapper;

import com.gmdiias.apistarwars.dto.PlanetRequestDTO;
import com.gmdiias.apistarwars.dto.PlanetResponseDTO;
import com.gmdiias.apistarwars.entity.Planet;

@Mapper(componentModel="spring")
public interface PlanetMapper {

	public Planet toPlanet(PlanetResponseDTO dto);
	
	public PlanetResponseDTO toPlanet(Planet planet);
	
	public Planet toPlanet(PlanetRequestDTO dto);
	
}
