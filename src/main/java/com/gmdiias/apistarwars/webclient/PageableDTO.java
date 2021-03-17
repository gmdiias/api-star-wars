package com.gmdiias.apistarwars.webclient;

import java.util.List;

import com.gmdiias.apistarwars.planeta.PlanetaDTO;

public class PageableDTO {

	private Long count;
	private List<PlanetaDTO> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<PlanetaDTO> getResults() {
		return results;
	}

	public void setResults(List<PlanetaDTO> results) {
		this.results = results;
	}
}
