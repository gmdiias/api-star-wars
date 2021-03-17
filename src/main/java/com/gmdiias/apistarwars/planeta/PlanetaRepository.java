package com.gmdiias.apistarwars.planeta;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

	Optional<Planeta> findByNomeIgnoreCase(String nomePlaneta);

}
