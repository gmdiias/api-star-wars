package com.gmdiias.apistarwars.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmdiias.apistarwars.dto.PageableStarWarsApiDTO;
import com.gmdiias.apistarwars.dto.PlanetRequestDTO;
import com.gmdiias.apistarwars.dto.PlanetResponseDTO;
import com.gmdiias.apistarwars.service.PlanetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/planet")
public class PlanetController {

	private final PlanetService service;

	public PlanetController(PlanetService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	@ApiResponse(responseCode = "200", description = "Planeta encontrado e retornado com sucesso.")
	@ApiResponse(responseCode = "400", description = "Planeta não encontrado no banco de dados.")
	@Operation(summary = "Busca um planeta através de seu ID.")
	public ResponseEntity<PlanetResponseDTO> getById(
			@PathVariable("id") @Parameter(description = "ID do planeta a ser buscado.") Long id) {
		PlanetResponseDTO planet = service.getById(id);
		return ResponseEntity.ok(planet);
	}

	@GetMapping("name/{search}")
	@ApiResponse(responseCode = "200", description = "Planeta encontrado e retornado com sucesso.")
	@ApiResponse(responseCode = "400", description = "Planeta não encontrado no banco de dados.")
	public ResponseEntity<PlanetResponseDTO> getByName(
			@PathVariable("search") @Parameter(description = "Nome do planeta a ser buscado.") String name) {
		PlanetResponseDTO planet = service.getByName(name);
		return ResponseEntity.ok(planet);
	}

	@GetMapping
	@ApiResponse(responseCode = "200", description = "Planetas retornados com sucesso.")
	@Operation(summary = "Busca todos os planetas salvos no banco de dados.")
	public List<PlanetResponseDTO> findAll() {
		return service.findAll();
	}

	@GetMapping("externalApi")
	@ApiResponse(responseCode = "200", description = "Planetas retornados com sucesso.")
	@Operation(summary = "Busca todos os planetas da API do Star Wars.")
	public ResponseEntity<PageableStarWarsApiDTO> findAllStarWarsApi(@RequestParam(value = "page") int page,
			@RequestParam(value = "search", required = false) String search) {
		return ResponseEntity.ok(service.findAllInSwApi(search, page));
	}

	@PostMapping
	@ApiResponse(responseCode = "201", description = "Planetas adicionado com sucesso.")
	@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao adicionar o planeta.")
	@Operation(summary = "Adiciona um novo planeta no banco de dados.")
	public ResponseEntity<PlanetResponseDTO> post(@RequestBody PlanetRequestDTO planeta) {
		PlanetResponseDTO planetaSaved = service.post(planeta);
		return ResponseEntity.status(HttpStatus.CREATED).body(planetaSaved);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "200", description = "Planetas removido com sucesso.")
	@ApiResponse(responseCode = "400", description = "Ocorreu um erro ao remover o planeta.")
	@Operation(summary = "Remove um planeta do banco de dados.")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
