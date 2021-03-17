package com.gmdiias.apistarwars.dto;

import java.util.List;

public class PageableDTO {

	private Long count;
	private List<PlanetSwDTO> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<PlanetSwDTO> getResults() {
		return results;
	}

	public void setResults(List<PlanetSwDTO> results) {
		this.results = results;
	}
}
