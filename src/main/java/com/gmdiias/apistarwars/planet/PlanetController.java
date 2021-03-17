package com.gmdiias.apistarwars.planet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planet")
public class PlanetController {

	@Autowired
	PlanetService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<PlanetDTO> getById(@PathVariable("id") Long id){
		PlanetDTO planeta = service.getById(id);
		return ResponseEntity.ok(planeta);
	}

	@GetMapping
	public List<PlanetDTO> findAll(){
		List<PlanetDTO> planetasFind = service.findAll();
		return planetasFind;
	}
	
	@PostMapping
	public ResponseEntity<PlanetDTO> post(@RequestBody PlanetDTO planeta) {
		PlanetDTO planetaSaved = service.post(planeta);
		return ResponseEntity.status(HttpStatus.CREATED).body(planetaSaved);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PlanetDTO> deleteById(@PathVariable("id") Long id){
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
}
