package br.com.alura.cibus.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CozinhaTest {

	@Test
	void deveriaFalharCasoNomeEstiverVazio() {
		Exception ex = assertThrows(Exception.class,() -> new Cozinha("Esse texto aqui deve conter ao menos 50 caracteres."));
		assertTrue(ex.getMessage().contains("O campo nome pode ter no máximo 50 caractéres!"));
	}
	
	@Test
	void deveriaFalharCasoNomeSejaVazio() {
		Exception ex = assertThrows(Exception.class, () -> new Cozinha(""));
		assertTrue(ex.getMessage().contains("O campo nome é obrigatório!"));
	}
	
	@Test
	void deveriaFalharCasoNomeSejaNulo() {
		Exception ex = assertThrows(Exception.class, () -> new Cozinha(null));
		assertTrue(ex.getMessage().contains("O campo nome é obrigatório!"));
	}
	
	@Test
	void deveriaTerSucessoAoCriarCozinhaComUmNomeValido() {
		Cozinha cozinha = new Cozinha("Espanhola");
		assertEquals("Espanhola", cozinha.getNome());
	}

}
