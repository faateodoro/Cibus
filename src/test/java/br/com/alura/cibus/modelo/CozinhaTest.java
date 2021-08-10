package br.com.alura.cibus.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CozinhaTest {

	@Test
	void deveria_falhar_caso_nome_estiver_vazio() {
		assertThrows(IllegalArgumentException.class,
				() -> new Cozinha("Esse texto aqui deve conter ao menos 50 caracteres."));
	}
	
	@Test
	void deveria_falhar_caso_nome_seja_vazio() {
		assertThrows(IllegalArgumentException.class, () -> new Cozinha(""));
	}
	
	@Test
	void deveria_falhar_caso_nome_seja_nulo() {
		assertThrows(IllegalArgumentException.class, () -> new Cozinha(null));
	}
	
	@Test
	void deveria_ter_sucesso_ao_criar_cozinha_com_um_nome_valido() {
		Cozinha cozinha = new Cozinha("Espanhola");
		assertEquals("Espanhola", cozinha.getNome());
	}

}