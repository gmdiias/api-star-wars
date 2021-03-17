package com.gmdiias.apistarwars.planet;

public class PlanetDTO {

	private Long id;
	private Long version;
	private String name;
	private String climate;
	private String terrain;
	private Long numFilmAppearances;

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

	public Long getNumFilmAppearances() {
		return numFilmAppearances;
	}

	public void setNumFilmAppearances(Long numFilmAppearances) {
		this.numFilmAppearances = numFilmAppearances;
	}

}
