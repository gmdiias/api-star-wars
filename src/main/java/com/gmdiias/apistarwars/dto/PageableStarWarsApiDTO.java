package com.gmdiias.apistarwars.dto;

import java.util.List;

public class PageableStarWarsApiDTO {

	private Long count;
	private String next;
	private String previous;
	private List<PlanetStarWarsApiDTO> results;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	public String getNext() {
		return next;
	}
	
	public void setNext(String next) {
		this.next = next;
	}
	
	public String getPrevious() {
		return previous;
	}
	
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public List<PlanetStarWarsApiDTO> getResults() {
		return results;
	}

	public void setResults(List<PlanetStarWarsApiDTO> results) {
		this.results = results;
	}
}
