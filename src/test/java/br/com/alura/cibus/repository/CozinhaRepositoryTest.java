package br.com.alura.cibus.repository;

import br.com.alura.cibus.modelo.Cozinha;
import org.junit.jupiter.api.BeforeEach;
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
	private Cozinha japonesa;

	@BeforeEach
	void beforeEach(){
		japonesa = entityManager.persist(new Cozinha("Japonesa"));
	}

	@Test
	void dado_nome_existente_no_banco_deve_retornar_cozinha() {
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

		assertThat(listaCozinhas)
				.hasSize(5)
				.extracting(Cozinha::getNome)
				.containsExactly("Árabe", "Baiana", "Chinesa", "Italiana", "Japonesa");
	}
	
	@Test
	public void deve_retornar_verdadeiro_se_a_cozinha_existir_no_banco_a_partir_do_nome() {
		boolean temCozinhaJaponesa = cozinhaRepository.existsByNomeIgnoreCase("Japonesa");

		assertTrue(temCozinhaJaponesa);
	}

	@Test
	public void deve_retornar_verdadeiro_se_a_cozinha_existir_no_banco_a_partir_do_nome_iniciado_com_minusculas() {
		boolean temCozinhaJaponesa = cozinhaRepository.existsByNomeIgnoreCase("japonesa");

		assertTrue(temCozinhaJaponesa);
	}

	@Test
	public void deve_retornar_falso_se_a_cozinha_nao_existir_no_banco_a_partir_do_nome() {
		boolean cozinhaNaoExiste = cozinhaRepository.existsByNomeIgnoreCase("Essa cozinha não existe");

		assertFalse(cozinhaNaoExiste);
	}
	
	@Test
	public void dado_nome_deve_retornar_verdadeiro_caso_o_id_seja_diferente() {
		boolean temCozinhaJaponesa = cozinhaRepository
				.existsByNomeIgnoreCaseAndIdNot("Japonesa", 54646346842L);

		assertTrue(temCozinhaJaponesa);
	}

	@Test
	public void dado_nome_deve_retornar_falso_caso_o_id_seja_igual() {
		boolean temCozinhaJaponesa = cozinhaRepository
				.existsByNomeIgnoreCaseAndIdNot("Japonesa", japonesa.getId());

		assertFalse(temCozinhaJaponesa);
	}

	@Test
	public void dado_nome_deve_retornar_falso_caso_o_nome_nao_exista() {
		boolean temCozinhaNaoExistente = cozinhaRepository
				.existsByNomeIgnoreCaseAndIdNot("Essa cozinha não existe", japonesa.getId());

		assertFalse(temCozinhaNaoExistente);
	}
}