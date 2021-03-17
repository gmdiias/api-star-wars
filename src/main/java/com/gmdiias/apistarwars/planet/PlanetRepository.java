package com.gmdiias.apistarwars.planet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlanetRepository extends JpaRepository<Planet, Long> {

	Optional<Planet> findByNameIgnoreCase(String name);

}
