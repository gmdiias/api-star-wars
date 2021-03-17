package com.gmdiias.apistarwars.dto;

import java.util.List;
import java.util.Objects;

public class PlanetSwDTO {

	private Long id;
	private Long version;
	private String name;
	private String climate;
	private String terrain;
	private List<String> films;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	
	public List<String> getFilms() {
		return films;
	}
	
	public void setFilms(List<String> films) {
		this.films = films;
	}

	public Long getNumFilmAppearances() {
		return Objects.nonNull(films) ? Long.valueOf(films.size()) : 0;
	}

}
