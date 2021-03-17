package com.gmdiias.apistarwars.planeta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/planeta")
public class PlanetaController {

	@Autowired
	PlanetaService planetaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<PlanetaDTO> getPlanetaById(@PathVariable("id") Long id){
		PlanetaDTO planeta = planetaService.getById(id);
		return ResponseEntity.ok(planeta);
	}

	@GetMapping
	public List<PlanetaDTO> findAll(){
		List<PlanetaDTO> planetasFind = planetaService.findAll();
		return planetasFind;
	}
	
	@PostMapping
	public ResponseEntity<PlanetaDTO> postPlaneta(@RequestBody PlanetaDTO planeta) {
		PlanetaDTO planetaSaved = planetaService.post(planeta);
		return ResponseEntity.ok(planetaSaved);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PlanetaDTO> deletePlanetaById(@PathVariable("id") Long id){
		planetaService.deletaPlanetaById(id);
		return ResponseEntity.ok().build();
	}
	
}
