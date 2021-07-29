package br.com.alura.cibus.repository;

import br.com.alura.cibus.modelo.Cozinha;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CozinhaRepositoryTest {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	void dado_nome_existente_no_banco_deve_retornar_cozinha() {
		entityManager.persist(new Cozinha("Japonesa"));

		Optional<Cozinha> cozinhaJaponesa = cozinhaRepository.findByNome("Japonesa");

		assertTrue(cozinhaJaponesa.isPresent());
		assertEquals("Japonesa", cozinhaJaponesa.get().getNome());
	}

	@Test
	void dado_nome_nao_existente_no_banco_nao_deve_retornar_cozinha() {
		Optional<Cozinha> cozinha = cozinhaRepository.findByNome("Nome que não existe");
		assertTrue(cozinha.isEmpty());
	}

	@Test
	 void deve_retornar_lista_de_cozinhas_ordenado_por_nome() {
		List<Cozinha> listaCozinhas = cozinhaRepository.findByOrderByNome();

		assertEquals(4, listaCozinhas.size());

		assertThat(listaCozinhas)
				.hasSize(4)
				.extracting(Cozinha::getNome)
				.containsExactly("Árabe", "Baiana", "Chinesa", "Italiana");
	}
	
	@Test
	public void deveRetornarVerdadeiroSeACozinhaExistirNoBancoAPartirDoNome() {
		boolean cozinhaJaponesa = cozinhaRepository.existsByNomeIgnoreCase("Japonesa");
		boolean cozinhaArabe = cozinhaRepository.existsByNomeIgnoreCase("Árabe");
		boolean cozinhaTailandesa = cozinhaRepository.existsByNomeIgnoreCase("Tailandesa");
		
		assertTrue(cozinhaJaponesa);
		assertTrue(cozinhaArabe);
		assertFalse(cozinhaTailandesa);
	}
	
	@Test
	public void dadoNomeDeveRetornarVerdadeiroCasoOIdSejaDiferente() {
		boolean cozinhaArabe = cozinhaRepository.existsByNomeIgnoreCaseAndIdNot("Árabe", 42L);
		boolean cozinhaJaponesa = cozinhaRepository.existsByNomeIgnoreCaseAndIdNot("Japonesa", 42L);
		boolean cozinhaJaponesaComDadosCertos = cozinhaRepository.existsByNomeIgnoreCaseAndIdNot("Japonesa", 5L);
		
		assertTrue(cozinhaArabe);
		assertTrue(cozinhaJaponesa);
		assertFalse(cozinhaJaponesaComDadosCertos);
	}

}
