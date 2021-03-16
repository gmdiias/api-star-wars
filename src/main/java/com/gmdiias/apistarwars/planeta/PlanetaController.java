package com.gmdiias.apistarwars.planeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/planeta")
public class PlanetaController {

	@Autowired
	PlanetaService planetaService;
	
	@PostMapping()
	public ResponseEntity<PlanetaDTO> postPlaneta(@RequestBody PlanetaDTO planeta) {
		PlanetaDTO planetaSaved = planetaService.salvaPlaneta(planeta);
		return ResponseEntity.ok(planetaSaved);
	}
}
