package com.gmdiias.apistarwars.planeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

}
