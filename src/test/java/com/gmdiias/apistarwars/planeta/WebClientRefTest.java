package com.gmdiias.apistarwars.planeta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gmdiias.apistarwars.webclient.WebClientRef;

@SpringBootTest
public class WebClientRefTest {

	@Autowired
	private WebClientRef webCliente;

	@Test
	public void dtoParaEntityTest() {
		webCliente.getPlanetaByNome("Tatooine");
	}
	

}
