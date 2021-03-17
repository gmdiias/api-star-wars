package com.gmdiias.apistarwars.webclient;

import java.util.List;

import com.gmdiias.apistarwars.planet.PlanetDTO;

public class PageableDTO {

	private Long count;
	private List<PlanetDTO> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<PlanetDTO> getResults() {
		return results;
	}

	public void setResults(List<PlanetDTO> results) {
		this.results = results;
	}
}
