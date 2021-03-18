package com.gmdiias.apistarwars.dto;

import java.util.List;

public class PageableStarWarsApiDTO {

	private Long count;
	private List<PlanetStarWarsApiDTO> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<PlanetStarWarsApiDTO> getResults() {
		return results;
	}

	public void setResults(List<PlanetStarWarsApiDTO> results) {
		this.results = results;
	}
}
