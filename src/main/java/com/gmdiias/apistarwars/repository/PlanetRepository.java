package com.gmdiias.apistarwars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gmdiias.apistarwars.entity.Planet;

@Transactional
public interface PlanetRepository extends JpaRepository<Planet, Long> {

	Optional<Planet> findFirstByNameStartsWithIgnoreCaseOrderByNameAsc(String name);

}
