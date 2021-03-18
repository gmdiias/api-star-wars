package com.gmdiias.apistarwars.dto;

import java.util.List;

public class PageableApiDTO {

	private Long count;
	private List<PlanetApDTO> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<PlanetApDTO> getResults() {
		return results;
	}

	public void setResults(List<PlanetApDTO> results) {
		this.results = results;
	}
}
